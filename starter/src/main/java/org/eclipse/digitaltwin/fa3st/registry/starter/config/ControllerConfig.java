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

import java.util.List;
import org.eclipse.digitaltwin.fa3st.registry.starter.helper.AssetKindConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Class with configuration for enum converters.
 * They are necessary for the request parameter.
 */
@Configuration
public class ControllerConfig implements WebMvcConfigurer {

    @Value("${cors.enabled:false}")
    private boolean corsEnabled;

    @Value("${cors.allowCredentials:false}")
    private boolean corsAllowCredentials;

    @Value("${cors.allowedOrigins:}")
    private List<String> corsAllowedOrigins;

    @Value("${cors.allowedMethods:}")
    private List<String> corsAllowedMethods;

    @Value("${cors.allowedHeaders:}")
    private List<String> corsAllowedHeaders;

    @Value("${cors.exposedHeaders:}")
    private List<String> corsExposedHeaders;

    @Value("${cors.maxAge:1800}")
    private long corsMaxAge;

    /**
     * The conversion service.
     *
     * @return the conversion service
     */
    @Bean
    protected ConversionService conversionService() {
        return new DefaultConversionService();
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (!corsEnabled) {
            return;
        }
        CorsRegistration registration = registry.addMapping("/api/v3.0/**");
        registration.allowedOrigins(corsAllowedOrigins.toArray(String[]::new));
        registration.allowedMethods(corsAllowedMethods.toArray(String[]::new));
        registration.allowedHeaders(corsAllowedHeaders.toArray(String[]::new));
        registration.exposedHeaders(corsExposedHeaders.toArray(String[]::new));
        registration.allowCredentials(corsAllowCredentials);
        registration.maxAge(corsMaxAge);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AssetKindConverter());
    }
}
