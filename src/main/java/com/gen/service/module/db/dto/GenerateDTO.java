package com.gen.service.module.db.dto;


import com.gen.service.module.db.vo.TableVO;
import com.gen.service.module.db.vo.VueConfigVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Database Structure", description="Tables, Views, Functions")
public class GenerateDTO {

    /**
     * DB
     */
    @ApiModelProperty(value = "DB", required = true)
    DbDTO db;

    /**
     * Table
     */
    @ApiModelProperty(value = "Table", required = true)
    TableVO table;

    /**
     * Config
     */
    @ApiModelProperty(value = "Config", required = true)
    ConfigDTO config;

    /**
     * Code Type
     */
    @ApiModelProperty(value = "Code Type", required = true)
    String codeType;
}
