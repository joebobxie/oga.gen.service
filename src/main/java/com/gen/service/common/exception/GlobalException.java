package com.gen.service.common.exception;

import com.gen.service.model.vo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    /**
     * Code
     */
    private Integer code;

    /**
     * Message
     */
    private String message;

    /**
     * From ApiRest
     *
     * @param api Api
     */
    public GlobalException(ApiRest<BaseVO> api) {
        this.code = api.getCode();
        this.message = api.getMessage();
    }

    /**
     * From ApiError
     *
     * @param api Api
     */
    public GlobalException(ApiError api) {
        this.code = api.code;
        this.message = api.message;
    }
}
