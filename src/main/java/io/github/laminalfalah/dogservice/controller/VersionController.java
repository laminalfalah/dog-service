package io.github.laminalfalah.dogservice.controller;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.controller
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

import io.github.laminalfalah.dogservice.properties.VersionProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 22/01/23
 */

@RestController
@RequestMapping(value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
@RequiredArgsConstructor
@Tag(name = "Version Controller", description = "Version Service")
public class VersionController implements InitializingBean {

    private final VersionProperties properties;

    @Setter
    @Getter
    private String message;

    @Override
    public void afterPropertiesSet() {
        setMessage(String.format("maven.groupId=%s\nmaven.artifactId=%s\nmaven.version=%s\nmaven.build-time=%s",
                properties.getGroupId(), properties.getArtifactId(), properties.getVersion(), properties.getBuildTime()));
    }

    @GetMapping
    public ResponseEntity<String> version() {
        return ResponseEntity.ok(getMessage());
    }

}
