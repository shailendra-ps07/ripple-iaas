package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.VMHardDisk;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface HardDiskDao {

    Optional<VMHardDisk> getByName(String name);
}
