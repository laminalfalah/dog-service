package io.github.laminalfalah.dogservice.exception;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.exception
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

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author laminalfalah on 22/01/23
 */

public class DogException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public DogException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public DogException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

}
