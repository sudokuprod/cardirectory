package com.sudokuprod.cardirectory.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@ApiModel(description = "Pojo object for information about exceptions")
@NoArgsConstructor
public class ExceptionPojo {
    private String message;
    private Date timestamp;
    private Integer status;

    public ExceptionPojo(Exception e, HttpStatus status) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "Internal server error";
    }
}