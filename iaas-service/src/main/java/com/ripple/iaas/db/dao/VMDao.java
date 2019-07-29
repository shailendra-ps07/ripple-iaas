package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.VirtualMachine;

import java.util.List;
import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface VMDao {

    VirtualMachine createVM(VirtualMachine virtualMachine);

    List<VirtualMachine> getVMsOfUser(String userId);

    List<VirtualMachine> getTopNVMsOfUser(String userId, int n);

    List<VirtualMachine> getTopNVMs(int n);

    Optional<VirtualMachine> getByName(String name);

}
