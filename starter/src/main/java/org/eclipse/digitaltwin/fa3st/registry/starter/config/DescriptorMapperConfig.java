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
package org.eclipse.digitaltwin.fa3st.registry.starter.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.core.internal.deserialization.EnumDeserializer;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.core.internal.serialization.EnumSerializer;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.core.internal.util.ReflectionHelper;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.internal.mixins.DataSpecificationIec61360Mixin;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.internal.mixins.EndpointMixin;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.internal.mixins.ExtensionMixin;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.internal.mixins.KeyMixin;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.internal.mixins.ReferenceMixin;
import org.eclipse.digitaltwin.aas4j.v3.model.AdministrativeInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.DataSpecificationContent;
import org.eclipse.digitaltwin.aas4j.v3.model.DataSpecificationIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.EmbeddedDataSpecification;
import org.eclipse.digitaltwin.aas4j.v3.model.Endpoint;
import org.eclipse.digitaltwin.aas4j.v3.model.Extension;
import org.eclipse.digitaltwin.aas4j.v3.model.Key;
import org.eclipse.digitaltwin.aas4j.v3.model.LangStringDefinitionTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.LangStringNameType;
import org.eclipse.digitaltwin.aas4j.v3.model.LangStringPreferredNameTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.LangStringShortNameTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.LangStringTextType;
import org.eclipse.digitaltwin.aas4j.v3.model.LevelType;
import org.eclipse.digitaltwin.aas4j.v3.model.ProtocolInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.Reference;
import org.eclipse.digitaltwin.aas4j.v3.model.SecurityAttributeObject;
import org.eclipse.digitaltwin.aas4j.v3.model.SpecificAssetId;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.ValueList;
import org.eclipse.digitaltwin.aas4j.v3.model.ValueReferencePair;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultAdministrativeInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultAssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultDataSpecificationIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultEmbeddedDataSpecification;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultEndpoint;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultExtension;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultKey;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLangStringDefinitionTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLangStringNameType;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLangStringPreferredNameTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLangStringShortNameTypeIec61360;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLangStringTextType;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultLevelType;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultProtocolInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultReference;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultSecurityAttributeObject;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultSpecificAssetId;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultSubmodelDescriptor;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultValueList;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultValueReferencePair;
import org.eclipse.digitaltwin.fa3st.common.dataformat.json.mixins.PageMixin;
import org.eclipse.digitaltwin.fa3st.common.dataformat.json.mixins.ServiceSpecificationProfileMixin;
import org.eclipse.digitaltwin.fa3st.common.model.ServiceSpecificationProfile;
import org.eclipse.digitaltwin.fa3st.common.model.api.paging.Page;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


/**
 * Class for configuring the classes to use for the descriptor interfaces.
 */
@Configuration
public class DescriptorMapperConfig {

    /**
     * Register our Mappings in the ObjectMapper.
     *
     * @return The desired Jackson2ObjectMapperBuilder.
     */
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        SimpleModule module = new SimpleModule("AasModel", Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(AssetAdministrationShellDescriptor.class, DefaultAssetAdministrationShellDescriptor.class);
        resolver.addMapping(AdministrativeInformation.class, DefaultAdministrativeInformation.class);
        resolver.addMapping(Endpoint.class, DefaultEndpoint.class);
        resolver.addMapping(ProtocolInformation.class, DefaultProtocolInformation.class);
        resolver.addMapping(SpecificAssetId.class, DefaultSpecificAssetId.class);
        resolver.addMapping(Key.class, DefaultKey.class);
        resolver.addMapping(Reference.class, DefaultReference.class);
        resolver.addMapping(SubmodelDescriptor.class, DefaultSubmodelDescriptor.class);
        resolver.addMapping(LangStringTextType.class, DefaultLangStringTextType.class);
        resolver.addMapping(LangStringNameType.class, DefaultLangStringNameType.class);
        resolver.addMapping(Extension.class, DefaultExtension.class);
        resolver.addMapping(EmbeddedDataSpecification.class, DefaultEmbeddedDataSpecification.class);
        resolver.addMapping(DataSpecificationIec61360.class, DefaultDataSpecificationIec61360.class);
        resolver.addMapping(LangStringPreferredNameTypeIec61360.class, DefaultLangStringPreferredNameTypeIec61360.class);
        resolver.addMapping(LangStringShortNameTypeIec61360.class, DefaultLangStringShortNameTypeIec61360.class);
        resolver.addMapping(LangStringDefinitionTypeIec61360.class, DefaultLangStringDefinitionTypeIec61360.class);
        resolver.addMapping(ValueList.class, DefaultValueList.class);
        resolver.addMapping(ValueReferencePair.class, DefaultValueReferencePair.class);
        resolver.addMapping(LevelType.class, DefaultLevelType.class);
        resolver.addMapping(DataSpecificationContent.class, DataSpecificationIec61360.class);
        resolver.addMapping(SecurityAttributeObject.class, DefaultSecurityAttributeObject.class);

        ReflectionHelper.ENUMS.forEach(x -> module.addSerializer(x, new EnumSerializer()));
        ReflectionHelper.ENUMS.forEach(x -> module.addDeserializer(x, new EnumDeserializer(x)));

        module.setAbstractTypes(resolver);
        return new Jackson2ObjectMapperBuilder()
                .modules(module)
                .mixIn(Page.class, PageMixin.class)
                .mixIn(Endpoint.class, EndpointMixin.class)
                .mixIn(DataSpecificationIec61360.class, DataSpecificationIec61360Mixin.class)
                .mixIn(Extension.class, ExtensionMixin.class)
                .mixIn(Key.class, KeyMixin.class)
                .mixIn(Reference.class, ReferenceMixin.class)
                .mixIn(ServiceSpecificationProfile.class, ServiceSpecificationProfileMixin.class)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToEnable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
                .dateFormat(new StdDateFormat().withColonInTimeZone(true));
    }
}
