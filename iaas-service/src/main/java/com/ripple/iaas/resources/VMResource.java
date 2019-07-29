package com.ripple.iaas.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ripple.iaas.application.auth.AuthUser;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.VirtualMachineDTO;
import com.ripple.iaas.models.request.VMRequestForm;
import com.ripple.iaas.services.UserService;
import com.ripple.iaas.services.VMService;
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
import java.util.List;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Slf4j
@Path("v1/iaas/vm/")
@Produces(MediaType.APPLICATION_JSON)
public class VMResource {

    private static Logger LOGGER = LoggerFactory.getLogger(VMResource.class);
    private final UserService userService;
    private final VMService vmService;

    @Inject
    public VMResource(UserService userService, VMService vmService) {
        this.userService = userService;
        this.vmService = vmService;
    }

    @POST
    @PermitAll
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response registerUser(@Auth AuthUser authUser, @Valid VMRequestForm vmRequestForm) throws IaaSServiceException {
        try {
            VirtualMachineDTO virtualMachineDTO = vmService.createVM(vmRequestForm, authUser.getName());
            return Response.ok(virtualMachineDTO).build();
        } catch (IaaSServiceException e) {
            return Response.status(e.getCustomError().getStatusType()).entity(e.getCustomError()).build();
        }
    }

    @GET
    @PermitAll
    @Path("/fetch")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response fetchAllVMsOfUser(@Auth AuthUser authUser) {
        List<VirtualMachineDTO> virtualMachineDTOList = vmService.fetchAllVMsOfUser(authUser.getName());
        return Response.ok(virtualMachineDTOList).build();
    }

    @GET
    @PermitAll
    @Path("/fetch/{N}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response fetchTopNVMsOfUser(@Auth AuthUser authUser, @PathParam("N") int n) {
        List<VirtualMachineDTO> virtualMachineDTOList = vmService.fetchTopNVMsOfUser(authUser.getName(), n);
        return Response.ok(virtualMachineDTOList).build();
    }

    @GET
    @PermitAll
    @Path("/fetch-all/{N}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response fetchTopNVMs(@Auth AuthUser authUser, @PathParam("N") int n) {
        List<VirtualMachineDTO> virtualMachineDTOList = vmService.fetchTopNVMs(n);
        return Response.ok(virtualMachineDTOList).build();
    }


}
