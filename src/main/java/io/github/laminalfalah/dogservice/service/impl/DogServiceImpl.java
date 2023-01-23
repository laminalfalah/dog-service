package io.github.laminalfalah.dogservice.service.impl;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.service.impl
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

import io.github.laminalfalah.dogservice.exception.DogException;
import io.github.laminalfalah.dogservice.json.request.DogRequest;
import io.github.laminalfalah.dogservice.json.response.DogResponse;
import io.github.laminalfalah.dogservice.mapper.DogMapper;
import io.github.laminalfalah.dogservice.repository.DogRepository;
import io.github.laminalfalah.dogservice.service.DogService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author laminalfalah on 21/01/23
 */

@Service
@Validated
@RequiredArgsConstructor
public class DogServiceImpl implements DogService {

    private static final String MESSAGE = "not-found";

    private final DogRepository repository;

    private final DogMapper mapper;

    @Setter
    @Getter
    private MessageSource messageSource;

    @Override
    public List<DogResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DogResponse store(DogRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public DogResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new DogException(MESSAGE, HttpStatus.NOT_FOUND));
    }

    @Override
    public DogResponse updateById(Long id, DogRequest request) {
        return repository.findById(id)
                .map(e -> mapper.toResponse(repository.save(mapper.toUpdate(e, request))))
                .orElseThrow(() -> new DogException(MESSAGE, HttpStatus.NOT_FOUND));
    }

    @Override
    public String deleteById(Long id) {
        return repository.findById(id)
                .map(v -> {
                    repository.delete(v);
                    return messageSource.getMessage("success-deleted", new Object[]{v.getName()}, Locale.getDefault());
                })
                .orElseThrow(() -> new DogException(MESSAGE, HttpStatus.NOT_FOUND));
    }

}
