package com.gen.service.common.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Global error codes
 * Used to define the response data of the interface.
 * All enumeration names are named using code, which is called in the system, eliminating the problem of naming difficulties.
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ApiError implements Serializable {

    Success(1, "Success"),
    Failed(0, "Failed"),
    Default(10000000, "Operation failed"),
    ServiceCallException(10000001, "Service call exception"),
    SystemBusy(10000002, "System busy"),
    Unauthorized(10000003, "Unauthorized"),
    UploadFailed(10000004, "Upload failed"),



    AccountExists(99999, "The account already exists.");

    /**
     * Code
     */
    public Integer code;
    /**
     * Message
     */
    public String message;

    /**
     * Generate Markdown format documents for use in document updates.
     */
    public static void main(String[] args) {
        for (ApiError e : ApiError.values()) {
            System.out.println("'" + e.code + "': '" + e.message + "',");
        }
    }
}
