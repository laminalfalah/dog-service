package io.github.laminalfalah.dogservice.json.request;

/*
 * Copyright (C) 2023 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.dogservice.json.request
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author laminalfalah on 21/01/23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogRequest implements Serializable {

    @NotNull
    @NotBlank
    @NotEmpty
    @CheckNameDog
    private String name;

}
