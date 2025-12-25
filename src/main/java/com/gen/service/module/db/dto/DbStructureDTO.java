package com.gen.service.module.db.dto;


import com.gen.service.model.dto.BaseDTO;
import com.gen.service.model.vo.BaseVO;
import com.gen.service.module.db.vo.DbConfigVO;
import com.gen.service.module.db.vo.TableVO;
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
public class DbStructureDTO extends BaseDTO {

    /**
     * DB Config
     */
    @ApiModelProperty(value = "DB Config", required = true)
    DbConfigDTO db;

    /**
     * Config
     */
    @ApiModelProperty(value = "Config", required = true)
    ConfigDTO config;
}
