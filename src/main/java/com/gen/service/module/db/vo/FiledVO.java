package com.gen.service.module.db.vo;

import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * File Describe
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Filed", description="Filed Describe")
public class FiledVO extends BaseVO {


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
     * Column Type
     */
    @ApiModelProperty(value = "Column Type")
    private String columnType;

    /**
     * DB Type
     */
    @ApiModelProperty(value = "DB Type")
    private String dbType;

    /**
     * Default Value
     */
    @ApiModelProperty(value = "Default Value")
    private String defaultValue;

    /**
     * Filed Describe
     */
    @ApiModelProperty(value = "Filed Describe")
    private String describe;

    /**
     * Filed Type
     */
    @ApiModelProperty(value = "Filed Type")
    private String fieldType;

    /**
     * Identity
     */
    @ApiModelProperty(value = "Identity")
    private Integer identity;

    /**
     * IsLeaf
     */
    @ApiModelProperty(value = "IsLeaf")
    private Boolean isLeaf;

    /**
     * Java Variable Name
     */
    @ApiModelProperty(value = "Java Variable Name")
    private String javaName;

    /**
     * Filed Name
     */
    @ApiModelProperty(value = "Filed Name")
    private String name;

    /**
     * Filed Nullable
     */
    @ApiModelProperty(value = "Filed Nullable")
    private Integer nullable;

    /**
     * Primary Key
     */
    @ApiModelProperty(value = "Primary Key")
    private Integer primary;

    /**
     * Filed Size
     */
    @ApiModelProperty(value = "Filed Size")
    private Long size;


}
