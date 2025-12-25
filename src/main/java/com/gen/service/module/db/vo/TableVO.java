package com.gen.service.module.db.vo;


import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Table
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Table", description="Data table")
public class TableVO extends BaseVO {
    /**
     * Display Name
     */
    @ApiModelProperty(value = "Display Name")
    private String label;

    /**
     * Java Class Name
     */
    @ApiModelProperty(value = "Java Class Name")
    private String className;

    /**
     * Table Describe
     */
    @ApiModelProperty(value = "Table Describe")
    private String describe;

    /**
     * Leaf
     */
    @ApiModelProperty(value = "Leaf")
    private Integer leaf;

    /**
     * Java Variable Name
     */
    @ApiModelProperty(value = "Java Variable Name")
    private String lowerClassName;

    /**
     * Table Name
     */
    @ApiModelProperty(value = "Table Name")
    private String name;

    /**
     * Vue Config
     */
    private VueConfigVO vueConfig;

    /**
     * Data Type
     */
    List<String> dbType;

    /**
     * Fields
     */
    private List<FiledVO> children;
}
