package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.VMRam;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface RamDao {

    Optional<VMRam> getByName(String name);
}
