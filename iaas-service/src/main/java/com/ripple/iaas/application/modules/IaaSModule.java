package com.ripple.iaas.application.modules;

import com.google.inject.AbstractModule;
import com.ripple.iaas.application.IaaSConfiguration;
import com.ripple.iaas.db.dao.*;
import com.ripple.iaas.db.dao.impl.*;
import com.ripple.iaas.services.AuthService;
import com.ripple.iaas.services.UserService;
import com.ripple.iaas.services.VMService;
import com.ripple.iaas.services.impl.AuthServiceImpl;
import com.ripple.iaas.services.impl.UserServiceImpl;
import com.ripple.iaas.services.impl.VMServiceImpl;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

/**
 * @author shailendra.ps
 * @since 29/04/19.
 */
public class IaaSModule extends AbstractModule {

    private HibernateBundle<IaaSConfiguration> hibernateBundle;
    private IaaSConfiguration                   iaaSConfiguration;

    public IaaSModule(HibernateBundle<IaaSConfiguration> hibernateBundle, IaaSConfiguration iaaSConfiguration) {
        this.hibernateBundle = hibernateBundle;
        this.iaaSConfiguration = iaaSConfiguration;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
        bind(UserService.class).to(UserServiceImpl.class);
        bind(AuthService.class).to(AuthServiceImpl.class);
        bind(VMService.class).to(VMServiceImpl.class);
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(CpuCoreDao.class).to(CpuCoresDaoImpl.class);
        bind(HardDiskDao.class).to(HardDiskDaoImpl.class);
        bind(OperatingSystemDao.class).to(OperatingSystemDaoImpl.class);
        bind(RamDao.class).to(RamDaoImpl.class);
        bind(VMDao.class).to(VMDaoImpl.class);
        bind(AuthTokenDao.class).to(MysqlAuthTokenDao.class);
    }

}
