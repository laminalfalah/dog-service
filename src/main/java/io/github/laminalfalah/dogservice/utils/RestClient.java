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

import io.github.laminalfalah.dogservice.exception.ClientStatusException;
import io.github.laminalfalah.dogservice.json.RestResponse;
import io.github.laminalfalah.dogservice.properties.CommonProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author laminalfalah on 23/01/23
 */

@Component
public class RestClient {

    @Setter
    private Duration timeout;

    @Getter(AccessLevel.PRIVATE)
    private final CommonProperties properties;

    @Getter(AccessLevel.PRIVATE)
    private final RestTemplate restTemplate;

    public RestClient(RestTemplateBuilder restTemplateBuilder, CommonProperties properties) {
        this.properties = properties;
        this.timeout = Duration.ofMillis(30000);
        this.restTemplate = restTemplateBuilder
                .setBufferRequestBody(true)
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();
    }

    private HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    public ResponseEntity<RestResponse> get(String url) {
        try {
            var response = getRestTemplate()
                    .exchange(
                            String.format("%s%s", properties.getRestUrl(), url),
                            HttpMethod.GET,
                            new HttpEntity<>(getHttpHeaders()),
                            RestResponse.class
                    );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ClientStatusException(
                        response.getBody() != null ? String.valueOf(response.getBody()) : "No matching route.",
                        response.getStatusCode()
                );
            }

            return response;
        } catch (HttpClientErrorException e) {
            throw new ClientStatusException(
                    e.getResponseBodyAsString(),
                    e.getStatusCode()
            );
        }
    }

}
