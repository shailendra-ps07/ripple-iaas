package com.ripple.iaas.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ripple.iaas.application.auth.AuthUser;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.UserDTO;
import com.ripple.iaas.models.request.UserRegistrationForm;
import com.ripple.iaas.models.response.AuthResponse;
import com.ripple.iaas.services.AuthService;
import com.ripple.iaas.services.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Slf4j
@Path("v1/iaas/user/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static Logger      LOGGER = LoggerFactory.getLogger(UserResource.class);
    private final  AuthService authService;
    private final  UserService userService;

    @Inject
    public UserResource(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response registerUser(@Valid UserRegistrationForm userRegistrationForm) throws IaaSServiceException {
        try {
            UserDTO      userDTO      = userService.registerUser(userRegistrationForm);
            AuthResponse authResponse = authService.registerUserToken(userDTO.getEmail());
            return Response.ok(authResponse).build();
        } catch (IaaSServiceException e) {
            return Response.status(e.getCustomError().getStatusType()).entity(e.getCustomError()).build();
        }
    }

    @DELETE
    @PermitAll
    @Path("/remove/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response registerUser(@Auth AuthUser authUser, @PathParam("userId") String userId) throws IaaSServiceException {
        userService.removeUser(authUser, userId);
        return Response.ok().build();
    }


}
