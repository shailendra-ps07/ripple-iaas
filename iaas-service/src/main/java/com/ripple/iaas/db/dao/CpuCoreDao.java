package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.VMCpuCores;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface CpuCoreDao {

    Optional<VMCpuCores> getByName(String name);
}
