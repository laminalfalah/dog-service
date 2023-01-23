package io.github.laminalfalah.dogservice.config;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.config
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
import io.github.laminalfalah.dogservice.swagger.ActuatorFactoryBean;
import io.github.laminalfalah.dogservice.swagger.ComponentsFactoryBean;
import io.github.laminalfalah.dogservice.swagger.OpenApiFactoryBean;
import io.github.laminalfalah.dogservice.swagger.ServiceFactoryBean;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author laminalfalah on 23/01/23
 */

@Configuration
@Profile("swagger")
@EnableConfigurationProperties({
        SpringDataWebProperties.class
})
public class SwaggerConfiguration {

    @Bean
    public ComponentsFactoryBean components(ApplicationContext ctx) {
        var parameters = ctx.getBeansOfType(Parameter.class);
        var securityScheme = ctx.getBeansOfType(SecurityScheme.class);
        var componentsFactoryBean = new ComponentsFactoryBean();
        componentsFactoryBean.setParameters(parameters);
        componentsFactoryBean.setSecurityScheme(securityScheme);
        return componentsFactoryBean;
    }

    @Bean
    public OpenApiFactoryBean openApi(Components components, SwaggerProperties properties) {
        var openApiFactoryBean = new OpenApiFactoryBean();
        openApiFactoryBean.setComponents(components);
        openApiFactoryBean.setProperties(properties);
        return openApiFactoryBean;
    }

    @Bean
    @ConditionalOnClass(ManagementServerProperties.class)
    @ConditionalOnMissingBean(ActuatorFactoryBean.class)
    public ActuatorFactoryBean actuatorApi(
            Components components,
            SwaggerProperties properties,
            @Value("${management.endpoints.web.base-path:/actuator}") String path
    ) {
        var actuatorFactoryBean = new ActuatorFactoryBean();
        actuatorFactoryBean.setComponents(components);
        actuatorFactoryBean.setProperties(properties);
        actuatorFactoryBean.setPath(path + "/**");
        return actuatorFactoryBean;
    }

    @Bean
    @ConditionalOnClass(SwaggerProperties.class)
    @ConditionalOnMissingBean(ServiceFactoryBean.class)
    public ServiceFactoryBean serviceFactoryBean(
            Components components,
            SwaggerProperties properties,
            @Value("${management.endpoints.web.base-path:/actuator}") String path
    ) {
        var serviceFactoryBean = new ServiceFactoryBean();
        serviceFactoryBean.setComponents(components);
        serviceFactoryBean.setProperties(properties);
        serviceFactoryBean.setPath(path + "/**");
        return serviceFactoryBean;
    }

    @Bean
    public SecurityScheme securitySwagger() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Bearer Token");
    }

}
