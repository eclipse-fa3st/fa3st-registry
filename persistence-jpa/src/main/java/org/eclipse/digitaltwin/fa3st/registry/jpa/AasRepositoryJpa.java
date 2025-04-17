/**
 * Copyright (c) 2025 the Eclipse FA³ST Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.digitaltwin.fa3st.registry.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetKind;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.common.util.Ensure;
import org.eclipse.digitaltwin.fa3st.registry.core.AbstractAasRepository;
import org.eclipse.digitaltwin.fa3st.registry.core.exception.ResourceAlreadyExistsException;
import org.eclipse.digitaltwin.fa3st.registry.core.exception.ResourceNotFoundException;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaAssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSubmodelDescriptorStandalone;
import org.eclipse.digitaltwin.fa3st.registry.jpa.util.EntityManagerHelper;
import org.eclipse.digitaltwin.fa3st.registry.jpa.util.ModelTransformationHelper;
import org.springframework.stereotype.Repository;


/**
 * Relational database implementation of the Repository.
 */
@Repository
@Transactional
public class AasRepositoryJpa extends AbstractAasRepository {

    @PersistenceContext(name = "AASRepositoryJPA")
    private final EntityManager entityManager;

    public AasRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<AssetAdministrationShellDescriptor> getAASs(String assetType, AssetKind assetKind) {
        return EntityManagerHelper.getAllAas(entityManager, assetType, assetKind);
    }


    @Override
    public AssetAdministrationShellDescriptor getAAS(String aasId) throws ResourceNotFoundException {
        Ensure.requireNonNull(aasId, "id must be non-null");
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        return aas;
    }


    @Override
    public AssetAdministrationShellDescriptor create(AssetAdministrationShellDescriptor descriptor) throws ResourceAlreadyExistsException {
        ensureDescriptorId(descriptor);
        AssetAdministrationShellDescriptor aas = fetchAAS(descriptor.getId());
        Ensure.require(Objects.isNull(aas), buildAASAlreadyExistsException(descriptor.getId()));
        JpaAssetAdministrationShellDescriptor result = ModelTransformationHelper.convertAAS(descriptor);
        entityManager.persist(result);
        return result;
    }


    @Override
    public void deleteAAS(String aasId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        entityManager.remove(aas);
    }


    @Override
    public AssetAdministrationShellDescriptor update(String aasId, AssetAdministrationShellDescriptor descriptor) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureDescriptorId(descriptor);
        JpaAssetAdministrationShellDescriptor aas = fetchAAS(descriptor.getId());
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        return entityManager.merge(new JpaAssetAdministrationShellDescriptor.Builder()
                .id(aas.getId())
                .from(descriptor)
                .build());
    }


    @Override
    public List<SubmodelDescriptor> getSubmodels(String aasId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        return aas.getSubmodelDescriptors();
    }


    @Override
    public List<SubmodelDescriptor> getSubmodels() {
        return EntityManagerHelper.getAll(entityManager, JpaSubmodelDescriptorStandalone.class, SubmodelDescriptor.class);
    }


    @Override
    public SubmodelDescriptor getSubmodel(String aasId, String submodelId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureSubmodelId(submodelId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));

        List<SubmodelDescriptor> submodels = aas.getSubmodelDescriptors();
        Optional<SubmodelDescriptor> submodel = submodels.stream()
                .filter(x -> Objects.nonNull(x.getId())
                        && Objects.equals(x.getId(), submodelId))
                .findAny();
        Ensure.require(submodel.isPresent(), buildSubmodelNotFoundInAASException(aasId, submodelId));
        return submodel.get();
    }


    @Override
    public SubmodelDescriptor getSubmodel(String submodelId) throws ResourceNotFoundException {
        ensureSubmodelId(submodelId);
        SubmodelDescriptor submodel = fetchSubmodelStandalone(submodelId);
        Ensure.requireNonNull(submodel, buildSubmodelNotFoundException(submodelId));
        return submodel;
    }


    @Override
    public SubmodelDescriptor addSubmodel(String aasId, SubmodelDescriptor descriptor) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        ensureAasId(aasId);
        ensureDescriptorId(descriptor);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        if (getSubmodelInternal(aas.getSubmodelDescriptors(), descriptor.getId()).isPresent()) {
            throw buildSubmodelAlreadyExistsException(descriptor.getId());
        }
        JpaSubmodelDescriptor submodel = ModelTransformationHelper.convertSubmodel(descriptor);
        aas.getSubmodelDescriptors().add(submodel);
        entityManager.merge(aas);
        return submodel;
    }


    @Override
    public SubmodelDescriptor addSubmodel(SubmodelDescriptor descriptor) throws ResourceAlreadyExistsException {
        ensureDescriptorId(descriptor);
        SubmodelDescriptor submodel = fetchSubmodelStandalone(descriptor.getId());
        Ensure.require(Objects.isNull(submodel), buildSubmodelAlreadyExistsException(descriptor.getId()));
        submodel = ModelTransformationHelper.convertSubmodelStandalone(descriptor);
        entityManager.persist(submodel);
        return submodel;
    }


    @Override
    public void deleteSubmodel(String aasId, String submodelId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureSubmodelId(submodelId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        Optional<SubmodelDescriptor> submodel = aas.getSubmodelDescriptors().stream()
                .filter(x -> Objects.equals(x.getId(), submodelId)
                        || (Objects.nonNull(x.getId())
                                && x.getId().equalsIgnoreCase(submodelId)))
                .findAny();
        Ensure.require(submodel.isPresent(), buildSubmodelNotFoundInAASException(aasId, submodelId));
        entityManager.remove(aas);
        aas.getSubmodelDescriptors().removeIf(x -> x.getId().equals(submodelId));
        entityManager.persist(aas);
    }


    @Override
    public void deleteSubmodel(String submodelId) throws ResourceNotFoundException {
        ensureSubmodelId(submodelId);
        SubmodelDescriptor submodel = fetchSubmodelStandalone(submodelId);
        Ensure.requireNonNull(submodel, buildSubmodelNotFoundException(submodelId));
        entityManager.remove(submodel);
    }


    private JpaAssetAdministrationShellDescriptor fetchAAS(String aasId) {
        try {
            return entityManager.find(JpaAssetAdministrationShellDescriptor.class, aasId);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }


    private JpaSubmodelDescriptorStandalone fetchSubmodelStandalone(String submodelId) {
        return entityManager.find(JpaSubmodelDescriptorStandalone.class, submodelId);
    }
}
