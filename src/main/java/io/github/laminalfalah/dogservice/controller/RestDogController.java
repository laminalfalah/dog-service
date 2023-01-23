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

import io.github.laminalfalah.dogservice.service.RestDogService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 23/01/23
 */

@RestController
@RequestMapping(value = "/rest/dog", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Rest Dog API", description = "Rest Dog API", externalDocs = @ExternalDocumentation(url = "https://dog.ceo/dog-api"))
public class RestDogController implements BaseController {

    private final RestDogService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return toJson(service.getAll());
    }

    @GetMapping("/{breed}")
    public ResponseEntity<?> getBreed(@PathVariable("breed") String breed) {
        return toJson(service.getBreed(breed));
    }

}
