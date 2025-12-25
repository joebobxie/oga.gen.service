package com.gen.service.module.db.dto;

import com.gen.service.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * Config
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Config", description="Config")
public class ConfigDTO extends BaseDTO {

    /**
     * Company Name
     */
    @ApiModelProperty(value = "Company Name", required = true)
    String company;

    /**
     * Author
     */
    @ApiModelProperty(value = "Author", required = true)
    String author;

    /**
     * Solution Name
     */
    @ApiModelProperty(value = "Solution Name", required = true)
    String solution;

    /**
     * Group ID
     */
    @ApiModelProperty(value = "Group ID", required = true)
    String groupId;

    /**
     * Artifact ID
     */
    @ApiModelProperty(value = "Artifact ID", required = true)
    String artifactId;

    /**
     * Version
     */
    @ApiModelProperty(value = "Version", required = true)
    String version;

    /**
     * Project Name
     */
    @ApiModelProperty(value = "Project Name", required = true)
    String name;

    /**
     * Project Description
     */
    @ApiModelProperty(value = "Project Description", required = true)
    String description;

    /**
     * Development Api
     */
    @ApiModelProperty(value = "Development Api", required = true)
    String devAPI;

    /**
     * Test Api
     */
    @ApiModelProperty(value = "Test Api", required = true)
    String testAPI;

    /**
     * Stage Api
     */
    @ApiModelProperty(value = "Stage Api", required = true)
    String stageAPI;

    /**
     * Production Api
     */
    @ApiModelProperty(value = "Production Api", required = true)
    String prodAPI;

    /**
     * Ignore the first section of the table
     */
    @ApiModelProperty(value = "Ignore the first section of the table ", required = true)
    boolean tableIgnore;

    /**
     * Package Name
     */
    @ApiModelProperty(value = "Package Name", required = true)
    String packageName;

    /**
     * Server Port
     */
    @ApiModelProperty(value = "Server Port", required = true)
    String port;

    /**
     * Development Config Namespace
     */
    @ApiModelProperty(value = "Development Config Namespace", required = true)
    String devConfigNamespace;

    /**
     * Development Discovery Namespace
     */
    @ApiModelProperty(value = "Development Discovery Namespace", required = true)
    String devDiscoveryNamespace;

    /**
     * Production Config Namespace
     */
    @ApiModelProperty(value = "Production Config Namespace", required = true)
    String prodConfigNamespace;

    /**
     * Production Discovery Namespace
     */
    @ApiModelProperty(value = "Production Discovery Namespace", required = true)
    String prodDiscoveryNamespace;

    /**
     * Development Discovery Config Address
     */
    @ApiModelProperty(value = "Development Discovery Config Address", required = true)
    String devDiscoveryConfigAddress;

    /**
     * Development Discovery Server Address
     */
    @ApiModelProperty(value = "Development Discovery Server Address", required = true)
    String devDiscoveryServerAddress;

    /**
     * Production Discovery Config Address
     */
    @ApiModelProperty(value = "Production Discovery Config Address", required = true)
    String prodDiscoveryConfigAddress;

    /**
     * Production Discovery Server Address
     */
    @ApiModelProperty(value = "Production Discovery Server Address", required = true)
    String prodDiscoveryServerAddress;

    /**
     * Development Shared Data IDs
     */
    @ApiModelProperty(value = "Development Shared Data IDs", required = true)
    String devSharedDataIds;

    /**
     * Production Shared Data IDs
     */
    @ApiModelProperty(value = "Production Shared Data IDs", required = true)
    String prodSharedDataIds;

    /**
     * App Name
     */
    @ApiModelProperty(value = "App Name", required = true)
    String appName;

    /**
     * Java Project Path
     */
    @ApiModelProperty(value = "Java Project Path", required = true)
    String path;

}
