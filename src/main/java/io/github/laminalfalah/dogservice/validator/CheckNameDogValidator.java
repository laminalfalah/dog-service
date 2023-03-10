package io.github.laminalfalah.dogservice.validator;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.validator
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

import io.github.laminalfalah.dogservice.annotation.CheckNameDog;
import io.github.laminalfalah.dogservice.repository.DogRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author laminalfalah on 22/01/23
 */

@RequiredArgsConstructor
public class CheckNameDogValidator implements ConstraintValidator<CheckNameDog, String> {

    private final DogRepository repository;

    private CheckNameDog checkNameDog;

    @Override
    public void initialize(CheckNameDog constraintAnnotation) {
        this.checkNameDog = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!repository.existsByName(s)) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(checkNameDog.message())
                .addConstraintViolation();

        return false;
    }

}
