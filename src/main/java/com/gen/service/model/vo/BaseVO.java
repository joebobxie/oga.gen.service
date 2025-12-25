package com.gen.service.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * The base class for responses
 * used for handling serialization.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Base class for responses", description="The base class for responses")
public class BaseVO implements Serializable {

}
