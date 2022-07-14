package com.sudokuprod.cardirectory.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "Pojo object for order by queries")
@NoArgsConstructor
@AllArgsConstructor
public class OrderPojo {
    @ApiModelProperty(required = true, notes = "Order direction type")
    private Sort.Direction direction;
    @ApiModelProperty(required = true, notes = "List of fields to include in order by query")
    private List<String> fields;
}
