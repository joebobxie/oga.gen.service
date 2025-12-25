package com.gen.service.module.db.vo;

import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * Filed Type
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Filed Type", description="Filed Type")
public class FiledTypeVO extends BaseVO {

    /**
     * File Name
     */
    @ApiModelProperty(value = "File Name")
    String name;

    /**
     * Default Value
     */
    @ApiModelProperty(value = "Default Value")
    String value;

    /**
     * DB Type
     */
    @ApiModelProperty(value = "DB Type")
    String dbType;
}
