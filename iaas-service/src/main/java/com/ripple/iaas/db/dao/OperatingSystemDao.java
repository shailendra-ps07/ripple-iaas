package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.OperatingSystem;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface OperatingSystemDao {

    Optional<OperatingSystem> getByName(String name);
}
