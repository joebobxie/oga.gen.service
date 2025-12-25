package com.gen.service.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * The base class for requests
 * used for handling serialization.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BaseIdDTO extends BaseDTO {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    private String id;

    /**
     * Region
     */
    @ApiModelProperty(value = "Region", hidden = true)
    private String region;

    /**
     * Class Overloading
     */
    public BaseIdDTO() {
    }

    /**
     * Class Overloading
     *
     * @param id ID
     */
    public BaseIdDTO(String id) {
        this.id = id;
    }
}
