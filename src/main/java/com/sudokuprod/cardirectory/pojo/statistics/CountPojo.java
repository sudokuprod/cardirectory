package com.sudokuprod.cardirectory.pojo.statistics;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel(description = "Pojo object for statistics on the number of requests")
public class CountPojo extends StatPojo {
    private final Long count;

    public CountPojo(String path, Long count) {
        super(path);
        this.count = count;
    }
}