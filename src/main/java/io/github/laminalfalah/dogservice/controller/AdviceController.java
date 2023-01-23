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

import io.github.laminalfalah.dogservice.exception.ClientStatusException;
import io.github.laminalfalah.dogservice.exception.DogException;
import io.github.laminalfalah.dogservice.utils.BindingResultHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

/**
 * @author laminalfalah on 22/01/23
 */

@ControllerAdvice(basePackageClasses = ErrorController.class)
@RestControllerAdvice(basePackageClasses = ErrorController.class)
@RequiredArgsConstructor
public class AdviceController extends ResponseEntityExceptionHandler implements BaseController {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(mapping(ex.getMessage(), status, null), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(mapping(BindingResultHelper.from(ex.getBindingResult(), messageSource), status, null), headers, status);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleWebExchangeBindException(WebExchangeBindException ex) {
        return toJson(BindingResultHelper.from(ex.getBindingResult(), messageSource), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        return toJson(BindingResultHelper.from(ex.getConstraintViolations()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DogException.class)
    public ResponseEntity<?> handleDogException(DogException ex, Locale locale) {
        return toJson(messageSource.getMessage(ex.getMessage(), null, locale), ex.getStatus());
    }

    @ExceptionHandler(ClientStatusException.class)
    public ResponseEntity<?> handleClientStatusException(ClientStatusException ex) {
        return toJson(ex.getMessage(), ex.getStatus());
    }

}
