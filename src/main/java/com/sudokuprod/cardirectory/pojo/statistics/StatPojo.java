package com.sudokuprod.cardirectory.pojo.statistics;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ApiModel(description = "Pojo object for statistics")
@RequiredArgsConstructor
public class StatPojo {
    private final String path;
}