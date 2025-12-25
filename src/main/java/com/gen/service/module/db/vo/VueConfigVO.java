package com.gen.service.module.db.vo;

import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Vue Config
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Vue Config", description="Vue Config")
public class VueConfigVO extends BaseVO {


    /**
     * Router
     */
    @ApiModelProperty(value = "Router")
    private String router;

    /**
     * catalog
     */
    @ApiModelProperty(value = "catalog")
    private String catalog;

    /**
     * Class Name
     */
    @ApiModelProperty(value = "Class Name")
    private String className;

    /**
     * Fun
     */
    @ApiModelProperty(value = "Fun")
    private String fun;

    /**
     * i18n
     */
    @ApiModelProperty(value = "i18n")
    private String i18n;

    /**
     * Java File Path
     */
    @ApiModelProperty(value = "Java File Path")
    private String javaPath;

    /**
     * path
     */
    @ApiModelProperty(value = "path")
    private String path;

    /**
     * Prefix
     */
    @ApiModelProperty(value = "Prefix")
    private String prefix;

    /**
     * Mapper File Path
     */
    @ApiModelProperty(value = "Mapper File Path")
    private String mapperPath;

    /**
     * Router Name
     */
    @ApiModelProperty(value = "Router Name")
    private String routerName;

    /**
     * Vue File Path
     */
    @ApiModelProperty(value = "Vue File Path")
    private String vuePath;


}
