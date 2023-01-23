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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.laminalfalah.dogservice.json.RestResponse;
import io.github.laminalfalah.dogservice.service.RestDogService;
import io.github.laminalfalah.dogservice.utils.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.*;

/**
 * @author laminalfalah on 23/01/23
 */

@Service
@Validated
@RequiredArgsConstructor
public class RestDogServiceImpl implements RestDogService {

    private final RestClient client;

    private final ObjectMapper objectMapper;

    @Override
    public Map<String, List<String>> getAll() {
        client.setTimeout(Duration.ofMillis(5000));

        return getDogs(client.get("/breeds/list/all").getBody());
    }

    @Override
    public Map<String, List<String>> getBreed(String breed) {
        client.setTimeout(Duration.ofMillis(2000));

        return getDogs(client.get(String.format("/breed/%s/list", breed)).getBody());
    }

    private Map<String, List<String>> getDogs(RestResponse restResponse) {
        var mapping = new HashMap<String, List<String>>();

        if (restResponse != null) {
            objectMapper.convertValue(restResponse.getMessage(), new TypeReference<Map<String, List<String>>>() {})
                    .forEach((k, v) -> {
                        if (!v.isEmpty()) {
                            v.forEach(j -> mapping.put(
                                    String.format("%s-%s", k, j),
                                    getImages(String.format("%s/%s", k, j)))
                            );
                        } else {
                            mapping.put(k, getImages(k));
                        }
                    });

            return mapping;
        }
        return Collections.emptyMap();
    }

    private List<String> getImages(String breed) {
        var mapping = new ArrayList<String>();

        var imagesResponse = client.get(String.format("/breed/%s/images/random/10", breed)).getBody();

        if (imagesResponse != null) {
            var i = 1;
            var a = objectMapper.convertValue(imagesResponse.getMessage(), new TypeReference<List<String>>() {});
            for (var image: a) {
                if (breed.equalsIgnoreCase("shiba")) {
                    if (i % 2 != 0) {
                        mapping.add(image);
                    }
                } else {
                    mapping.add(image);
                }
                i++;
            }

            return mapping;
        }

        return Collections.emptyList();
    }

}
