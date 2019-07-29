package com.ripple.iaas.application;

import com.ripple.iaas.db.entities.*;
import io.dropwizard.Configuration;
import io.dropwizard.hibernate.HibernateBundle;

/**
 * @author shailendra.ps
 * @since 29/04/19.
 */
public abstract class AbstractHibernateBundle<T extends Configuration> extends HibernateBundle<T> {
    protected AbstractHibernateBundle() {
        super(User.class, VirtualMachine.class,
                VMRam.class, VMHardDisk.class, VMCpuCores.class, OperatingSystem.class,
                AuthToken.class);
    }
}
