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

import io.github.laminalfalah.dogservice.properties.SwaggerProperties;
import io.github.laminalfalah.dogservice.utils.SwaggerInfo;
import io.swagger.v3.oas.models.Components;
import lombok.Setter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author laminalfalah on 23/01/23
 */

public class ActuatorFactoryBean implements FactoryBean<GroupedOpenApi> {

    @Setter
    private SwaggerProperties properties;

    @Setter
    private Components components;

    @Setter
    private String path;

    @Override
    public GroupedOpenApi getObject() {
        var info = new SwaggerInfo(properties);
        return GroupedOpenApi.builder()
                .group("Actuator")
                .addOpenApiCustomiser(openApi -> {
                    openApi.setInfo(info.info("Actuator"));
                    openApi.setComponents(components);
                    openApi.setServers(properties.getServers());
                })
                .pathsToMatch(path)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return GroupedOpenApi.class;
    }

}
