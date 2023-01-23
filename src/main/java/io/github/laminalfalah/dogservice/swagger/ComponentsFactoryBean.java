package io.github.laminalfalah.dogservice.swagger;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.swagger
 *
 * This is part of the dog-service.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * @author laminalfalah on 23/01/23
 */

public class ComponentsFactoryBean implements FactoryBean<Components> {

    @Setter
    private Map<String, Parameter> parameters;

    @Setter
    private Map<String, SecurityScheme> securityScheme;

    @Override
    public Components getObject() {
        var components = new Components();
        components.setParameters(parameters);
        components.setSecuritySchemes(securityScheme);
        return components;
    }

    @Override
    public Class<?> getObjectType() {
        return Components.class;
    }

}
