package com.bmy.wx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName NewsQueryTerm
 * @Description 查询排序方式vo
 * @Author potato
 * @Date 2020/12/23 下午9:27
 **/
@Data
@ApiModel(value = "OrderTerm" ,description = "查询排序方式")
public class QuerySortTerm {

    @ApiModelProperty(value = "排序方式键值对",name = "sort",example = "{'type':'publish_time','value':'desc'}")
    private Map<String,String> sort;

}
