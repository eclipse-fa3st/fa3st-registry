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

/**
 * Registry Descriptor JPA implementation for Submodel of an AAS.
 */
public class JpaSubmodelDescriptor extends JpaSubmodelDescriptorBase {

    public abstract static class AbstractBuilder<T extends JpaSubmodelDescriptor, B extends AbstractBuilder<T, B>>
            extends JpaSubmodelDescriptorBase.AbstractBuilder<T, B> {}

    public static class Builder extends AbstractBuilder<JpaSubmodelDescriptor, Builder> {

        @Override
        protected Builder getSelf() {
            return this;
        }


        @Override
        protected JpaSubmodelDescriptor newBuildingInstance() {
            return new JpaSubmodelDescriptor();
        }
    }
}
