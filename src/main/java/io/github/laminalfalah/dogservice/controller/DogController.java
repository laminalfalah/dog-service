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

import io.github.laminalfalah.dogservice.json.request.DogRequest;
import io.github.laminalfalah.dogservice.service.DogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author laminalfalah on 21/01/23
 */

@RestController
@RequestMapping(path = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Dog Controller", description = "CRUD")
public class DogController implements BaseController {

    private final DogService service;

    @GetMapping
    public ResponseEntity<?> getAll(@ParameterObject Pageable pageable) {
        return toJson(service.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody DogRequest request) {
        return toJson(service.store(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return toJson(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id, @Valid @RequestBody DogRequest request) {
        return toJson(service.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return toJson(null, service.deleteById(id));
    }

}
