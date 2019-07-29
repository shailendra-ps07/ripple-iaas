package com.ripple.iaas.services;

import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.VirtualMachineDTO;
import com.ripple.iaas.models.request.VMRequestForm;

import java.util.List;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface VMService {

    /**
     * create VM for user
     * @param vmRequestForm
     * @param userId
     * @return VM Model
     * @throws IaaSServiceException
     */
    VirtualMachineDTO createVM(VMRequestForm vmRequestForm, String userId) throws IaaSServiceException;

    /**
     * fetch VM Oof user
     * @param userId
     * @return list of VM Model
     */
    List<VirtualMachineDTO> fetchAllVMsOfUser(String userId);

    /**
     * fetch top VM by Ram for user
     * @param userId
     * @param n
     * @return list of VM Model
     */
    List<VirtualMachineDTO> fetchTopNVMsOfUser(String userId, int n);

    /**
     * fetch top VM by Ram across all users
     * @param n
     * @return list of VM Model
     */
    List<VirtualMachineDTO> fetchTopNVMs(int n);
}
