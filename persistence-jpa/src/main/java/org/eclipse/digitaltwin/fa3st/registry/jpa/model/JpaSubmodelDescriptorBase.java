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
package org.eclipse.digitaltwin.fa3st.registry.jpa.model;

import java.util.Objects;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.builder.SubmodelDescriptorBuilder;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultSubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.jpa.util.ModelTransformationHelper;


/**
 * Registry Descriptor JPA implementation Base class for Submodel.
 */
public abstract class JpaSubmodelDescriptorBase extends DefaultSubmodelDescriptor {

    protected JpaSubmodelDescriptorBase() {}

    public abstract static class AbstractBuilder<T extends JpaSubmodelDescriptorBase, B extends AbstractBuilder<T, B>>
            extends SubmodelDescriptorBuilder<T, B> {

        public B from(SubmodelDescriptor other) {
            if (Objects.nonNull(other)) {
                id(other.getId());
                idShort(other.getIdShort());
                endpoints(ModelTransformationHelper.convertEndpoints(other.getEndpoints()));
                administration(ModelTransformationHelper.convertAdministrativeInformation(other.getAdministration()));
                description(ModelTransformationHelper.convertDescriptions(other.getDescription()));
                displayName(ModelTransformationHelper.convertDisplayNames(other.getDisplayName()));
                semanticId(ModelTransformationHelper.convertReference(other.getSemanticId()));
                extensions(ModelTransformationHelper.convertExtensions(other.getExtensions()));
                supplementalSemanticId(ModelTransformationHelper.convertReferences(other.getSupplementalSemanticId()));
            }
            return getSelf();
        }

    }
}
