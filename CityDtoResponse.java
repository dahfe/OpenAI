package com.gpt.spring_gpt_4o.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDtoResponse {

    @NotNull
    private String name;

    @NotNull
    private String state;

    private int population;

    private double latitude;

    private double longitude;
}

