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

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * @author laminalfalah on 22/01/23
 */

public final class BindingResultHelper {

    private BindingResultHelper() {}

    public static Map<String, List<String>> from(BindingResult result, MessageSource message) {
        return from(result, message, LocaleContextHolder.getLocale());
    }

    protected static Map<String, List<String>> from(BindingResult result, MessageSource message, Locale locale) {
        if (result.hasFieldErrors()) {
            var map = new HashMap<String, List<String>>();
            result.getFieldErrors().forEach(fieldError -> {
                var field = fieldError.getField();

                if (!map.containsKey(field)) {
                    map.put(field, new ArrayList<>());
                }

                var errorMessage = message.getMessage(
                        Objects.requireNonNull(fieldError.getCode()),
                        fieldError.getArguments(),
                        fieldError.getDefaultMessage(),
                        locale
                );

                map.get(field).add(errorMessage);
            });

            return map;
        } else {
            return Collections.emptyMap();
        }
    }

    public static Map<String, List<String>> from(Set<ConstraintViolation<?>> violations) {
        var map = new HashMap<String, List<String>>();

        violations.forEach(violation -> {
            for (var attr: getAttribute(violation)) {
                if (!map.containsKey(attr)) {
                    map.put(attr, new ArrayList<>());
                }
                map.get(attr).add(violation.getMessage());
            }
        });

        return map;
    }

    private static String[] getAttribute(ConstraintViolation<?> violation) {
        var values = (String[]) violation.getConstraintDescriptor().getAttributes().get("path");
        return values == null || values.length == 0 ? getAttributePath(violation) : values;
    }

    private static String[] getAttributePath(ConstraintViolation<?> violation) {
        var path = violation.getPropertyPath();

        var builder = new StringBuilder();

        path.forEach(node -> {
            if (node.getName() != null) {
                if (builder.length() > 0) {
                    builder.append(".");
                }
                builder.append(node.getName());
            }
        });
        return new String[]{builder.toString()};
    }

}
