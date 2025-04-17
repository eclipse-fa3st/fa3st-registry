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
package org.eclipse.digitaltwin.fa3st.registry.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.fa3st.registry.core.AbstractAasRepositoryTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = AasRepositoryJpaTest.class)
@DataJpaTest
@EntityScan(basePackages = {
        "org.eclipse.digitaltwin.fa3st.registry.jpa.model"
})
public class AasRepositoryJpaTest extends AbstractAasRepositoryTest<AasRepositoryJpa> {

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setup() {
        repository = new AasRepositoryJpa(entityManager);
    }


    @Override
    protected void compareSubmodel(SubmodelDescriptor expected, SubmodelDescriptor actual) {
        RecursiveComparisonConfiguration ignoreIdConfig = new RecursiveComparisonConfiguration();
        ignoreIdConfig.ignoreFields("id");
        assertThat(expected)
                .usingRecursiveComparison(ignoreIdConfig)
                .isEqualTo(actual);
    }

}
