package com.ripple.iaas.services;

import com.ripple.iaas.application.auth.AuthUser;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.UserDTO;
import com.ripple.iaas.models.request.UserRegistrationForm;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface UserService {

    /**
     * fetch user by email id
     * @param emailId
     * @return user model
     */
    Optional<UserDTO> getUserByEmailId(String emailId);

    /**
     * Register new user
     * @param userRegistrationForm
     * @return user model
     * @throws IaaSServiceException
     */
    UserDTO registerUser(UserRegistrationForm userRegistrationForm) throws IaaSServiceException;

    /**
     * Delete User
     * @param authUser
     * @param userId
     * @throws IaaSServiceException
     */
    void removeUser(AuthUser authUser, String userId) throws IaaSServiceException;
}
