package com.gen.service.module.db.dto;

import com.gen.service.model.dto.BaseDTO;
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
public class DbConfigDTO extends BaseDTO {


    /**
     * Name
     */
    @ApiModelProperty(value = "Name", required = true)
    private String name;

    /**
     * DB Name
     */
    @ApiModelProperty(value = "DB Name", required = true)
    private String dbName;

    /**
     * Host
     */
    @ApiModelProperty(value = "Host", required = true)
    private String host;

    /**
     * Password
     */
    @ApiModelProperty(value = "Password", required = true)
    private String password;

    /**
     * Port
     */
    @ApiModelProperty(value = "Port", required = true)
    private String port;

    /**
     * Table Name
     */
    @ApiModelProperty(value = "Table Name", required = true)
    private String tableName;

    /**
     * User Name
     */
    @ApiModelProperty(value = "User Name", required = true)
    private String userName;

    /**
     * Connection Url
     */
    public String getUrl() {
        return String.format("jdbc:mysql://%s:%s/?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",
                host,
                port);
    }
}
