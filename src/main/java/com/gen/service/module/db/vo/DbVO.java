package com.gen.service.module.db.vo;

import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Database
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Database", description="Database")
public class DbVO extends BaseVO {


    /**
     * Name
     */
    @ApiModelProperty(value = "Name")
    private String name;

    /**
     * Charset
     */
    @ApiModelProperty(value = "Charset")
    private String charset;

    /**
     * Display Name
     */
    @ApiModelProperty(value = "Display Name")
    private String label;

    /**
     * Leaf
     */
    @ApiModelProperty(value = "Leaf")
    private Integer leaf;


}
