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
package org.eclipse.digitaltwin.fa3st.registry.jpa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.digitaltwin.aas4j.v3.model.AdministrativeInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShellDescriptor;
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
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaAdministrativeInformation;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaAssetAdministrationShellDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaDataSpecificationIec61360;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaDescription;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaDisplayName;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaEmbeddedDataSpecification;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaEndpoint;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaExtension;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaKey;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaLangStringDefinitionTypeIec61360;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaLangStringPreferredNameTypeIec61360;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaLangStringShortNameTypeIec61360;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaLevelType;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaProtocolInformation;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaReference;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSecurityAttributeObject;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSpecificAssetId;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaString;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaSubmodelDescriptorStandalone;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaValueList;
import org.eclipse.digitaltwin.fa3st.registry.jpa.model.JpaValueReferencePair;


/**
 * Helper class to transform AAS model classes to JPA model classes.
 */
public class ModelTransformationHelper {

    private ModelTransformationHelper() {}


    /**
     * Converts AssetAdministrationShellDescriptor to JPAAssetAdministrationShellDescriptor.
     *
     * @param aas The AssetAdministrationShellDescriptor.
     * @return The converted JPAAssetAdministrationShellDescriptor.
     */
    public static JpaAssetAdministrationShellDescriptor convertAAS(AssetAdministrationShellDescriptor aas) {
        if (aas == null) {
            return null;
        }
        return new JpaAssetAdministrationShellDescriptor.Builder()
                .from(aas)
                .build();
    }


    /**
     * Converts AdministrativeInformation to JPAAdministrativeInformation.
     *
     * @param administrativeInformation The AdministrativeInformation.
     * @return The converted JPAAdministrativeInformation.
     */
    public static JpaAdministrativeInformation convertAdministrativeInformation(AdministrativeInformation administrativeInformation) {
        if (administrativeInformation == null) {
            return null;
        }
        return new JpaAdministrativeInformation.Builder()
                .from(administrativeInformation)
                .build();
    }


    /**
     * Converts a list of LangStringTextType to a list of JPALangStringTextType.
     *
     * @param descriptions The list of descriptions.
     * @return The converted list of descriptions.
     */
    public static List<LangStringTextType> convertDescriptions(List<LangStringTextType> descriptions) {
        return descriptions.stream()
                .map(x -> new JpaDescription.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of LangStringNameType to a list of JPALangStringNameType.
     *
     * @param names The list of names.
     * @return The converted list of names.
     */
    public static List<LangStringNameType> convertDisplayNames(List<LangStringNameType> names) {
        return names.stream()
                .map(x -> new JpaDisplayName.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of Endpoint to a list of JPAEndpoint.
     *
     * @param endpoints The list of Endpoint.
     * @return The converted list of JPAEndpoint.
     */
    public static List<Endpoint> convertEndpoints(List<Endpoint> endpoints) {
        if (Objects.isNull(endpoints)) {
            return null;
        }
        return endpoints.stream()
                .map(x -> new JpaEndpoint.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of SpecificAssetIds to a list of JPASpecificAssetId.
     *
     * @param pairs The list of SpecificAssetId.
     * @return The converted list of JPASpecificAssetId.
     */
    public static List<SpecificAssetId> convertSpecificAssetIds(List<SpecificAssetId> pairs) {
        if (Objects.isNull(pairs)) {
            return null;
        }
        return pairs.stream()
                .map(x -> new JpaSpecificAssetId.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of Key to a list of JPAKey.
     *
     * @param keys The list of Key.
     * @return The converted list of JPAKey.
     */
    public static List<Key> convertKeys(List<Key> keys) {
        if (Objects.isNull(keys)) {
            return null;
        }
        return keys.stream()
                .map(x -> new JpaKey.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts ProtocolInformation to JPAProtocolInformation.
     *
     * @param protocolInformation The ProtocolInformation.
     * @return The converted JPAProtocolInformation.
     */
    public static JpaProtocolInformation convertProtocolInformation(ProtocolInformation protocolInformation) {
        if (protocolInformation == null) {
            return null;
        }
        return new JpaProtocolInformation.Builder().from(protocolInformation).build();
    }


    /**
     * Converts Reference to JPAReference.
     *
     * @param reference The Reference.
     * @return The converted JPAReference.
     */
    public static JpaReference convertReference(Reference reference) {
        if (reference == null) {
            return null;
        }
        return new JpaReference.Builder()
                .from(reference)
                .build();
    }


    /**
     * Converts Reference to JPAReference.
     *
     * @param references The list of References.
     * @return The converted list of References.
     */
    public static List<Reference> convertReferences(List<Reference> references) {
        if (Objects.isNull(references)) {
            return new ArrayList<>();
        }
        return references.stream()
                .map(x -> new JpaReference.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts SubmodelDescriptor to JPASubmodelDescriptor.
     *
     * @param submodel The SubmodelDescriptor.
     * @return The converted JPASubmodelDescriptor.
     */
    public static JpaSubmodelDescriptor convertSubmodel(SubmodelDescriptor submodel) {
        if (submodel == null) {
            return null;
        }
        return new JpaSubmodelDescriptor.Builder().from(submodel).build();
    }


    /**
     * Converts SubmodelDescriptor to JPASubmodelDescriptorStandalone.
     *
     * @param submodel The SubmodelDescriptor.
     * @return The converted JPASubmodelDescriptor.
     */
    public static JpaSubmodelDescriptorStandalone convertSubmodelStandalone(SubmodelDescriptor submodel) {
        if (submodel == null) {
            return null;
        }
        return new JpaSubmodelDescriptorStandalone.Builder().from(submodel).build();
    }


    /**
     * Converts a list of SubmodelDescriptor to a list of JPASubmodelDescriptor.
     *
     * @param submodels The list of SubmodelDescriptor.
     * @return The converted list of JPASubmodelDescriptor.
     */
    public static List<SubmodelDescriptor> convertSubmodels(List<SubmodelDescriptor> submodels) {
        if (Objects.isNull(submodels)) {
            return null;
        }
        return submodels.stream()
                .map(x -> new JpaSubmodelDescriptor.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts Extension to JPAExtension.
     *
     * @param extension The Extension.
     * @return The converted JPAExtension.
     */
    public static JpaExtension convertExtension(Extension extension) {
        if (extension == null) {
            return null;
        }
        return new JpaExtension.Builder()
                .from(extension)
                .build();
    }


    /**
     * Converts Extension to JPAExtension.
     *
     * @param extensions The list of Extensions.
     * @return The converted list of Extensions.
     */
    public static List<Extension> convertExtensions(List<Extension> extensions) {
        if (Objects.isNull(extensions)) {
            return new ArrayList<>();
        }
        return extensions.stream()
                .map(x -> new JpaExtension.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of LangStringPreferredNameTypeIec61360 to a list of JpaLangStringPreferredNameTypeIec61360.
     *
     * @param names The list of names.
     * @return The converted list of names.
     */
    public static List<LangStringPreferredNameTypeIec61360> convertPreferredNameIec61360(List<LangStringPreferredNameTypeIec61360> names) {
        return names.stream()
                .map(x -> new JpaLangStringPreferredNameTypeIec61360.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of LangStringShortNameTypeIec61360 to a list of JpaLangStringShortNameTypeIec61360.
     *
     * @param names The list of names.
     * @return The converted list of names.
     */
    public static List<LangStringShortNameTypeIec61360> convertShortNameIec61360(List<LangStringShortNameTypeIec61360> names) {
        return names.stream()
                .map(x -> new JpaLangStringShortNameTypeIec61360.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of LangStringDefinitionTypeIec61360 to a list of JpaLangStringDefinitionTypeIec61360.
     *
     * @param names The list of names.
     * @return The converted list of names.
     */
    public static List<LangStringDefinitionTypeIec61360> convertDefinitionIec61360(List<LangStringDefinitionTypeIec61360> names) {
        return names.stream()
                .map(x -> new JpaLangStringDefinitionTypeIec61360.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a list of ValueReferencePair to a list of JpaValueReferencePair.
     *
     * @param pairs The list of pairs.
     * @return The converted list of pairs.
     */
    public static List<ValueReferencePair> convertValueReferencePairs(List<ValueReferencePair> pairs) {
        return pairs.stream()
                .map(x -> new JpaValueReferencePair.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Converts a ValueList to a JpaValueList.
     *
     * @param value the desired ValueList.
     * @return The converted ValueList.
     */
    public static ValueList convertValueList(ValueList value) {
        if (value == null) {
            return null;
        }
        return new JpaValueList.Builder()
                .from(value)
                .build();
    }


    /**
     * Converts a DataSpecificationIec61360 into JpaDataSpecificationIec61360.
     *
     * @param iec The desired DataSpecificationIec61360.
     * @return The converted JPA DataSpecificationIec61360.
     */
    public static DataSpecificationIec61360 convertDataSpecificationIec61360(DataSpecificationIec61360 iec) {
        if (iec == null) {
            return null;
        }
        return new JpaDataSpecificationIec61360.Builder().from(iec).build();
    }


    /**
     * Converts LevelType to JpaLevelType.
     *
     * @param levelType The LevelType.
     * @return The converted LevelType.
     */
    public static JpaLevelType convertLevelType(LevelType levelType) {
        if (levelType == null) {
            return null;
        }
        return new JpaLevelType.Builder()
                .from(levelType)
                .build();
    }


    /**
     * Convert EmbeddedDataSpecification to JpaEmbeddedDataSpecification.
     *
     * @param embeddedDataSpecification The EmbeddedDataSpecification.
     * @return The converted EmbeddedDataSpecification.
     */
    public static EmbeddedDataSpecification convertEmbeddedDataSpecification(EmbeddedDataSpecification embeddedDataSpecification) {
        if (embeddedDataSpecification == null) {
            return null;
        }
        return new JpaEmbeddedDataSpecification.Builder()
                .from(embeddedDataSpecification)
                .build();
    }


    /**
     * Convert a list of EmbeddedDataSpecifications to a list of JpaEmbeddedDataSpecification.
     *
     * @param embeddedDataSpecifications The list of EmbeddedDataSpecifications.
     * @return The converted list of EmbeddedDataSpecifications.
     */
    public static List<EmbeddedDataSpecification> convertEmbeddedDataSpecifications(List<EmbeddedDataSpecification> embeddedDataSpecifications) {
        return embeddedDataSpecifications.stream()
                .map(x -> new JpaEmbeddedDataSpecification.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Convert a list of SecurityAttributeObjects to a list of JpaSecurityAttributeObjects.
     *
     * @param securityAttributes The list of SecurityAttributeObject.
     * @return The converted list of SecurityAttributeObjects.
     */
    public static List<SecurityAttributeObject> convertSecurityAttributes(List<SecurityAttributeObject> securityAttributes) {
        return securityAttributes.stream()
                .map(x -> new JpaSecurityAttributeObject.Builder().from(x).build())
                .collect(Collectors.toList());
    }


    /**
     * Convert a list of Strings to a list of JpaStrings.
     *
     * @param versions The list of Strings.
     * @return The converted list of Strings.
     */
    public static List<JpaString> convertStrings(List<String> versions) {
        return versions.stream()
                .map(x -> new JpaString.Builder().value(x).build())
                .collect(Collectors.toList());
    }
}
