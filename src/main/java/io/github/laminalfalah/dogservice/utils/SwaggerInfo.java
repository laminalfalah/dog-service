package io.github.laminalfalah.dogservice.utils;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.utils
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
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;

/**
 * @author laminalfalah on 23/01/23
 */

@RequiredArgsConstructor
public class SwaggerInfo {

    private final SwaggerProperties properties;

    public Info info(String groupName) {
        var info = new Info();
        info.setTitle(groupName + " " + properties.getTitle());
        info.setDescription(properties.getDescription());
        info.setTermsOfService(properties.getTermsOfService());
        info.setVersion(properties.getVersion());
        info.setContact(contact());
        info.setLicense(license());
        return info;
    }

    private Contact contact() {
        var contact = new Contact();
        contact.setName(properties.getContact().getName());
        contact.setEmail(properties.getContact().getEmail());
        contact.setUrl(properties.getContact().getUrl());
        return contact;
    }

    private License license() {
        var license = new License();
        license.setName(properties.getLicense().getName());
        license.setUrl(properties.getLicense().getUrl());
        return license;
    }

}
