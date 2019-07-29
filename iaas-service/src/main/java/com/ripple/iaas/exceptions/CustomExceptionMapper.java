package com.ripple.iaas.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author shailendra.ps
 * @since 30/07/19.
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<IaaSServiceException> {
    @Override
    public Response toResponse(IaaSServiceException exception) {
        return Response.status(exception.getCustomError().getStatusType())
                .entity(exception.getCustomError())
                .build();
    }
}
