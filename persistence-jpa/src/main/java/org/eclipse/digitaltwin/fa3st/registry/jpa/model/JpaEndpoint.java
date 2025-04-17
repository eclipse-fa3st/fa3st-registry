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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import org.eclipse.digitaltwin.aas4j.v3.model.Endpoint;
import org.eclipse.digitaltwin.aas4j.v3.model.builder.EndpointBuilder;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultEndpoint;
import org.eclipse.digitaltwin.fa3st.registry.jpa.util.ModelTransformationHelper;


/**
 * Registry Descriptor JPA implementation for Endpoint.
 */
public class JpaEndpoint extends DefaultEndpoint {

    @JsonIgnore
    private String id;

    public JpaEndpoint() {
        id = null;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        else if (this.getClass() != obj.getClass()) {
            return false;
        }
        else {
            JpaEndpoint other = (JpaEndpoint) obj;
            return super.equals(obj)
                    && Objects.equals(this.id, other.id);
        }
    }

    public abstract static class AbstractBuilder<T extends JpaEndpoint, B extends AbstractBuilder<T, B>> extends EndpointBuilder<T, B> {

        public B id(String value) {
            getBuildingInstance().setId(value);
            return getSelf();
        }


        public B from(Endpoint other) {
            if (other != null) {
                _interface(other.get_interface());
                protocolInformation(ModelTransformationHelper.convertProtocolInformation(other.getProtocolInformation()));
            }
            return getSelf();
        }
    }

    public static class Builder extends AbstractBuilder<JpaEndpoint, Builder> {

        @Override
        protected Builder getSelf() {
            return this;
        }


        @Override
        protected JpaEndpoint newBuildingInstance() {
            return new JpaEndpoint();
        }
    }
}
