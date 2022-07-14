package com.sudokuprod.cardirectory.pojo.statistics;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel(description = "Pojo object for statistics on average query execution time")
public class AvgExecutionTimePojo extends StatPojo {
    private final Long time;

    public AvgExecutionTimePojo(String path, Long time) {
        super(path);
        this.time = time;
    }
}
