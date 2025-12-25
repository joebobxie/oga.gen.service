package com.gen.service.module.db.vo;


import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Database Structure
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Database Structure", description="Tables, Views, Functions")
public class DbStructureVO extends BaseVO {

    /**
     * Table List
     */
    @ApiModelProperty(value = "Table List")
    List<TableVO> table;

    /**
     * View List
     */
    @ApiModelProperty(value = "View List")
    List<TableVO> view;

    /**
     * Function List
     */
    @ApiModelProperty(value = "Function List")
    List<TableVO> fun;
}
