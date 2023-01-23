package io.github.laminalfalah.dogservice.service;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.service
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
import io.github.laminalfalah.dogservice.json.response.DogResponse;
import org.springframework.context.MessageSourceAware;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author laminalfalah on 21/01/23
 */

public interface DogService extends MessageSourceAware {

    @Transactional(readOnly = true)
    List<DogResponse> getAll(Pageable pageable);

    @Transactional(rollbackFor = RuntimeException.class)
    DogResponse store(DogRequest request);

    @Transactional(readOnly = true)
    DogResponse getById(Long id);

    @Transactional(rollbackFor = RuntimeException.class)
    DogResponse updateById(Long id, DogRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
    String deleteById(Long id);

}
