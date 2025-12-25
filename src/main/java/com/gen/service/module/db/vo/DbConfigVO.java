package com.gen.service.module.db.vo;

import com.gen.service.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * DB Connection
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DB Connection", description="DB Connection")
public class DbConfigVO extends BaseVO {


    /**
     * Name
     */
    @ApiModelProperty(value = "Name")
    private String name;

    /**
     * DB Name
     */
    @ApiModelProperty(value = "DB Name")
    private String dbName;

    /**
     * Host
     */
    @ApiModelProperty(value = "Host")
    private String host;

    /**
     * Password
     */
    @ApiModelProperty(value = "Password")
    private String password;

    /**
     * Port
     */
    @ApiModelProperty(value = "Port")
    private String port;

    /**
     * Table Name
     */
    @ApiModelProperty(value = "Table Name")
    private String tableName;

    /**
     * User Name
     */
    @ApiModelProperty(value = "User Name")
    private String userName;
}
