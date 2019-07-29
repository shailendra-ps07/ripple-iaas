package com.ripple.iaas.exceptions;

import lombok.Data;

/**
 * @author shailendra.ps
 * @since 30/07/19.
 */
@Data
public class IaaSServiceException extends Exception {

    private CustomError customError;

    public IaaSServiceException() {
        super();
    }

    public IaaSServiceException(String msg) {
        super(msg);
    }

    public IaaSServiceException(Throwable cause) {
        super(cause);
    }

    public IaaSServiceException(CustomError error) {
        super();
        customError = error;
    }
}
