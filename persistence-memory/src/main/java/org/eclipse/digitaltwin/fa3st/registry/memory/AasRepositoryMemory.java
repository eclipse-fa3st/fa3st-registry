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
package org.eclipse.digitaltwin.fa3st.registry.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetKind;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.common.util.Ensure;
import org.eclipse.digitaltwin.fa3st.registry.core.AbstractAasRepository;
import org.eclipse.digitaltwin.fa3st.registry.core.exception.ResourceAlreadyExistsException;
import org.eclipse.digitaltwin.fa3st.registry.core.exception.ResourceNotFoundException;


/**
 * In-memory implementation of the Repository.
 */
public class AasRepositoryMemory extends AbstractAasRepository {

    private final Map<String, AssetAdministrationShellDescriptor> shellDescriptors;
    private final Map<String, SubmodelDescriptor> submodelDescriptors;

    public AasRepositoryMemory() {
        shellDescriptors = new HashMap<>();
        submodelDescriptors = new HashMap<>();
    }


    /**
     * Clear the repository.
     */
    public void clear() {
        shellDescriptors.clear();
        submodelDescriptors.clear();
    }


    @Override
    public List<AssetAdministrationShellDescriptor> getAASs(String assetType, AssetKind assetKind) {
        return new ArrayList<>(
                shellDescriptors.values().stream().filter(a -> filterAssetType(a, assetType)).filter(b -> filterAssetKind(b, assetKind)).toList());
    }


    @Override
    public AssetAdministrationShellDescriptor getAAS(String id) throws ResourceNotFoundException {
        Ensure.requireNonNull(id, "id must be non-null");
        AssetAdministrationShellDescriptor aas = fetchAAS(id);
        Ensure.requireNonNull(aas, buildAASNotFoundException(id));
        return aas;
    }


    @Override
    public AssetAdministrationShellDescriptor create(AssetAdministrationShellDescriptor descriptor) throws ResourceAlreadyExistsException {
        ensureDescriptorId(descriptor);
        AssetAdministrationShellDescriptor aas = fetchAAS(descriptor.getId());
        Ensure.require(Objects.isNull(aas), buildAASAlreadyExistsException(descriptor.getId()));
        shellDescriptors.put(descriptor.getId(), descriptor);
        return descriptor;
    }


    @Override
    public void deleteAAS(String aasId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        shellDescriptors.remove(aasId);
    }


    @Override
    public AssetAdministrationShellDescriptor update(String aasId, AssetAdministrationShellDescriptor descriptor) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureDescriptorId(descriptor);
        AssetAdministrationShellDescriptor oldAAS = getAAS(aasId);
        if (Objects.nonNull(oldAAS)) {
            shellDescriptors.remove(aasId);
        }
        shellDescriptors.put(descriptor.getId(), descriptor);
        return descriptor;
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
        return new ArrayList<>(submodelDescriptors.values());
    }


    @Override
    public SubmodelDescriptor getSubmodel(String aasId, String submodelId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureSubmodelId(submodelId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        List<SubmodelDescriptor> submodels = aas.getSubmodelDescriptors();
        Optional<SubmodelDescriptor> submodel = getSubmodelInternal(submodels, submodelId);
        Ensure.require(submodel.isPresent(), buildSubmodelNotFoundInAASException(aasId, submodelId));
        return submodel.get();
    }


    @Override
    public SubmodelDescriptor getSubmodel(String submodelId) throws ResourceNotFoundException {
        ensureSubmodelId(submodelId);
        Ensure.require(submodelDescriptors.containsKey(submodelId), buildSubmodelNotFoundException(submodelId));
        return submodelDescriptors.get(submodelId);
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
        aas.getSubmodelDescriptors().add(descriptor);
        return descriptor;
    }


    @Override
    public SubmodelDescriptor addSubmodel(SubmodelDescriptor descriptor) throws ResourceAlreadyExistsException {
        ensureDescriptorId(descriptor);
        Ensure.require(
                !submodelDescriptors.containsKey(descriptor.getId()),
                buildSubmodelAlreadyExistsException(descriptor.getId()));
        submodelDescriptors.put(descriptor.getId(), descriptor);
        return descriptor;
    }


    @Override
    public void deleteSubmodel(String aasId, String submodelId) throws ResourceNotFoundException {
        ensureAasId(aasId);
        ensureSubmodelId(submodelId);
        AssetAdministrationShellDescriptor aas = fetchAAS(aasId);
        Ensure.requireNonNull(aas, buildAASNotFoundException(aasId));
        boolean found = aas.getSubmodelDescriptors().removeIf(x -> Objects.equals(x.getId(), submodelId));
        Ensure.require(found, buildSubmodelNotFoundException(submodelId));
        submodelDescriptors.remove(submodelId);
    }


    @Override
    public void deleteSubmodel(String submodelId) throws ResourceNotFoundException {
        ensureSubmodelId(submodelId);
        Ensure.require(submodelDescriptors.containsKey(submodelId), buildSubmodelNotFoundException(submodelId));
        submodelDescriptors.remove(submodelId);
    }


    private AssetAdministrationShellDescriptor fetchAAS(String aasId) {
        ensureAasId(aasId);
        return shellDescriptors.getOrDefault(aasId, null);
    }


    private static boolean filterAssetType(AssetAdministrationShellDescriptor aas, String assetType) {
        if (assetType == null) {
            return true;
        }
        else {
            return aas.getAssetType().equals(assetType);
        }
    }


    private static boolean filterAssetKind(AssetAdministrationShellDescriptor aas, AssetKind assetKind) {
        if (assetKind == null) {
            return true;
        }
        else {
            return aas.getAssetKind() == assetKind;
        }
    }
}
