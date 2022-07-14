package com.sudokuprod.cardirectory.controller;

import com.sudokuprod.cardirectory.model.ApiLog;
import com.sudokuprod.cardirectory.pojo.statistics.AvgExecutionTimePojo;
import com.sudokuprod.cardirectory.pojo.statistics.CountPojo;
import com.sudokuprod.cardirectory.service.LogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {
    private final LogService logService;

    @ApiOperation("Get avg execution time")
    @ApiResponse(code = 200, response = ApiLog.class, message = "Avg execution time")
    @RequestMapping(method = RequestMethod.GET, path = "/avgExecTime")
    public AvgExecutionTimePojo avgExecutionTime(@RequestParam String path) {
        long avgExecutionTime = logService.avgExecutionTime(path);
        return new AvgExecutionTimePojo(path, avgExecutionTime);
    }

    @ApiOperation("Get the number of requests in one path")
    @ApiResponse(code = 200, response = ApiLog.class, message = "Number of requests")
    @RequestMapping(method = RequestMethod.GET, path = "/count")
    public CountPojo requestCount(@RequestParam String path) {
        long count = logService.count(path);
        return new CountPojo(path, count);
    }

}
