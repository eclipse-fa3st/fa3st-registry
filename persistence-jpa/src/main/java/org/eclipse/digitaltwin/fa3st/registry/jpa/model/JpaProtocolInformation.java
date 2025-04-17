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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.eclipse.digitaltwin.aas4j.v3.model.ProtocolInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.builder.ProtocolInformationBuilder;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultProtocolInformation;
import org.eclipse.digitaltwin.fa3st.registry.jpa.util.ModelTransformationHelper;


/**
 * Registry Descriptor JPA implementation for ProtocolInformation.
 */
public class JpaProtocolInformation extends DefaultProtocolInformation {

    @JsonIgnore
    private String id;

    @JsonIgnore
    private List<JpaString> jpaEndpointProtocolVersion;

    public JpaProtocolInformation() {
        id = null;
        jpaEndpointProtocolVersion = new ArrayList<>();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public List<JpaString> getJpaEndpointProtocolVersion() {
        return jpaEndpointProtocolVersion;
    }


    /**
     * Sets the list of EndpointProtocolVersions.
     *
     * @param jpaEndpointProtocolVersion The list of EndpointProtocolVersions.
     */
    public void setJpaEndpointProtocolVersion(List<JpaString> jpaEndpointProtocolVersion) {
        this.jpaEndpointProtocolVersion = jpaEndpointProtocolVersion;
        List<String> versions = new ArrayList<>();
        for (var v: jpaEndpointProtocolVersion) {
            versions.add(v.getValue());
        }
        setEndpointProtocolVersion(versions);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, jpaEndpointProtocolVersion);
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
            JpaProtocolInformation other = (JpaProtocolInformation) obj;
            return super.equals(obj)
                    && Objects.equals(this.id, other.id)
                    && Objects.equals(this.jpaEndpointProtocolVersion, other.jpaEndpointProtocolVersion);
        }
    }

    public abstract static class AbstractBuilder<T extends JpaProtocolInformation, B extends AbstractBuilder<T, B>>
            extends ProtocolInformationBuilder<T, B> {

        public B id(String value) {
            getBuildingInstance().setId(value);
            return getSelf();
        }


        public B jpaEndpointProtocolVersion(List<JpaString> value) {
            getBuildingInstance().setJpaEndpointProtocolVersion(value);
            return getSelf();
        }


        public B from(ProtocolInformation other) {
            if (Objects.nonNull(other)) {
                endpointProtocol(other.getEndpointProtocol());
                // endpointProtocolVersion is set in jpaEndpointProtocolVersion
                jpaEndpointProtocolVersion(ModelTransformationHelper.convertStrings(other.getEndpointProtocolVersion()));
                href(other.getHref());
                securityAttributes(ModelTransformationHelper.convertSecurityAttributes(other.getSecurityAttributes()));
                subprotocol(other.getSubprotocol());
                subprotocolBody(other.getSubprotocolBody());
                subprotocolBodyEncoding(other.getSubprotocolBodyEncoding());
            }
            return getSelf();
        }
    }

    public static class Builder extends AbstractBuilder<JpaProtocolInformation, Builder> {

        @Override
        protected Builder getSelf() {
            return this;
        }


        @Override
        protected JpaProtocolInformation newBuildingInstance() {
            return new JpaProtocolInformation();
        }
    }
}
