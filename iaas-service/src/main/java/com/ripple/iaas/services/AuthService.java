package com.ripple.iaas.services;

import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.response.AuthResponse;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface AuthService {

    /**
     * Get JWT Token from storage
     * @param userId
     * @return
     * @throws IaaSServiceException
     */
    AuthResponse getJWTToken(String userId) throws IaaSServiceException;

    /**
     * Register New Token
     * @param userId
     * @return Token
     * @throws IaaSServiceException
     */
    AuthResponse registerUserToken(String userId) throws IaaSServiceException;
}
