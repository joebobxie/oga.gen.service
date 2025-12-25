package com.gen.service.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Serializable DTO base class
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SerializeDTO implements Serializable {
}
