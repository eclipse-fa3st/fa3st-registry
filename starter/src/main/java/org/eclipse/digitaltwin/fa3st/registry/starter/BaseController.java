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
package org.eclipse.digitaltwin.fa3st.registry.starter;

import org.eclipse.digitaltwin.fa3st.common.util.EncodingHelper;
import org.eclipse.digitaltwin.fa3st.registry.core.exception.BadRequestException;


/**
 * Base class for all registry controllers.
 */
public class BaseController {

    /**
     * Decodes as base64Url-encoded id.
     *
     * @param encodedId the encoded id
     * @return the decoded id
     * @throws BadRequestException if the id is not properly encoded
     */
    protected String decodeBase64UrlId(String encodedId) {
        try {
            return EncodingHelper.base64UrlDecode(encodedId);
        }
        catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    String.format("id '%s' is not correctly base64URL-encoded (error: %s)", encodedId, e.getMessage()),
                    e);
        }
    }
}
