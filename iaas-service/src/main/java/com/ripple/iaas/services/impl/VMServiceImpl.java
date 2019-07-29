package com.ripple.iaas.services.impl;

import com.google.inject.Inject;
import com.ripple.iaas.db.dao.*;
import com.ripple.iaas.db.entities.*;
import com.ripple.iaas.exceptions.CustomError;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.VirtualMachineDTO;
import com.ripple.iaas.models.request.VMRequestForm;
import com.ripple.iaas.services.VMService;
import org.modelmapper.ModelMapper;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class VMServiceImpl implements VMService {

    private final VMDao              vmDao;
    private final RamDao             ramDao;
    private final OperatingSystemDao operatingSystemDao;
    private final HardDiskDao        hardDiskDao;
    private final CpuCoreDao         cpuCoreDao;
    private final UserDao            userDao;
    private final ModelMapper        modelMapper;

    @Inject
    public VMServiceImpl(VMDao vmDao, RamDao ramDao, OperatingSystemDao operatingSystemDao, HardDiskDao hardDiskDao, CpuCoreDao cpuCoreDao, UserDao userDao, ModelMapper modelMapper) {
        this.vmDao = vmDao;
        this.ramDao = ramDao;
        this.operatingSystemDao = operatingSystemDao;
        this.hardDiskDao = hardDiskDao;
        this.cpuCoreDao = cpuCoreDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    public VirtualMachineDTO createVM(VMRequestForm vmRequestForm, String userId) throws IaaSServiceException {
        VirtualMachine  virtualMachine = new VirtualMachine();

        Optional<User> userOptional = userDao.getById(userId, null);
        if (!userOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.NOT_FOUND)
                    .message("User is not registered")
                    .build();
            throw new IaaSServiceException(error);
        }

        Optional<VirtualMachine> virtualMachineOptional = vmDao.getByName(vmRequestForm.getName());
        if (virtualMachineOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.CONFLICT)
                    .message("VM with same name is already created. Please use different name")
                    .build();
            throw new IaaSServiceException(error);
        }

        Optional<VMRam> vmRamOptional  = ramDao.getByName(vmRequestForm.getRam());
        if (!vmRamOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.NOT_FOUND)
                    .message("Ram is not provisioned to use")
                    .build();
            throw new IaaSServiceException(error);
        }
        Optional<OperatingSystem> operatingSystemOptional = operatingSystemDao.getByName(vmRequestForm.getOperatingSystem());
        if (!operatingSystemOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.NOT_FOUND)
                    .message("OS is not provisioned to use")
                    .build();
            throw new IaaSServiceException(error);
        }
        Optional<VMHardDisk> hardDiskOptional = hardDiskDao.getByName(vmRequestForm.getHardDisk());
        if (!hardDiskOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.NOT_FOUND)
                    .message("Hard Disk is not provisioned to use")
                    .build();
            throw new IaaSServiceException(error);
        }
        Optional<VMCpuCores> cpuCoresOptional = cpuCoreDao.getByName(vmRequestForm.getCpuCores());
        if (!cpuCoresOptional.isPresent()) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.NOT_FOUND)
                    .message("CPU Core is not provisioned to use")
                    .build();
            throw new IaaSServiceException(error);
        }
        virtualMachine.setName(vmRequestForm.getName());
        virtualMachine.setCpuCores(cpuCoresOptional.get());
        virtualMachine.setHardDisk(hardDiskOptional.get());
        virtualMachine.setOperatingSystem(operatingSystemOptional.get());
        virtualMachine.setRam(vmRamOptional.get());
        virtualMachine.setUser(userOptional.get());
        virtualMachine = vmDao.createVM(virtualMachine);
        return modelMapper.map(virtualMachine, VirtualMachineDTO.class);
    }

    public List<VirtualMachineDTO> fetchAllVMsOfUser(String userId) {
        List<VirtualMachine> virtualMachineList = vmDao.getVMsOfUser(userId);
        return virtualMachineList.stream().map(
                virtualMachine -> modelMapper.map(virtualMachine, VirtualMachineDTO.class)
            ).collect(Collectors.toList());
    }

    public List<VirtualMachineDTO> fetchTopNVMsOfUser(String userId, int n) {
        List<VirtualMachine> virtualMachineList = vmDao.getTopNVMsOfUser(userId, n);
        return virtualMachineList.stream().map(
                virtualMachine -> modelMapper.map(virtualMachine, VirtualMachineDTO.class)
        ).collect(Collectors.toList());
    }

    public List<VirtualMachineDTO> fetchTopNVMs(int n) {
        List<VirtualMachine> virtualMachineList = vmDao.getTopNVMs(n);
        return virtualMachineList.stream().map(
                virtualMachine -> modelMapper.map(virtualMachine, VirtualMachineDTO.class)
        ).collect(Collectors.toList());
    }

}
