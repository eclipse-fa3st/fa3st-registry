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
package org.eclipse.digitaltwin.fa3st.registry.starter.helper;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetKind;
import org.springframework.core.convert.converter.Converter;


/**
 * Test converter for AssetKind.
 */
public class AssetKindConverter implements Converter<String, AssetKind> {

    @Override
    public AssetKind convert(String source) {
        return AssetKind.valueOf(org.eclipse.digitaltwin.aas4j.v3.dataformat.core.internal.deserialization.EnumDeserializer.deserializeEnumName(source));
    }
}
