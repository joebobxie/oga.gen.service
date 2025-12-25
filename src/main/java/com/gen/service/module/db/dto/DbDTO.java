package com.gen.service.module.db.dto;

import com.gen.service.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * DB
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DB", description="DB")
public class DbDTO extends BaseDTO {

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
