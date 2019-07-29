package com.ripple.iaas.db.dao.impl;

import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.VMDao;
import com.ripple.iaas.db.entities.User;
import com.ripple.iaas.db.entities.VMRam;
import com.ripple.iaas.db.entities.VirtualMachine;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

import static com.ripple.iaas.Constants.SIZE;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class VMDaoImpl extends AbstractDAO<VirtualMachine> implements VMDao {

    @Inject
    public VMDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public VirtualMachine createVM(VirtualMachine virtualMachine) {

        currentSession().save(virtualMachine);
        return virtualMachine;
    }

    public List<VirtualMachine> getVMsOfUser(String userId) {
        CriteriaQuery<VirtualMachine> criteriaQuery = criteriaQuery();
        CriteriaBuilder               builder       = currentSession().getCriteriaBuilder();
        Root<VirtualMachine>          from          = criteriaQuery.from(VirtualMachine.class);
        Join<VirtualMachine, VMRam>   ram           = from.join(Constants.VMConstants.RAM, JoinType.LEFT);
        Join<VirtualMachine, User>    user          = from.join(Constants.VMConstants.USER, JoinType.LEFT);
        criteriaQuery
                .select(from)
                .where(builder.equal(user.get(Constants.UsersConstants.EMAIL_ID), userId))
                .orderBy(builder.desc(ram.get(SIZE)));
        return currentSession().createQuery(criteriaQuery).getResultList();
    }

    public List<VirtualMachine> getTopNVMsOfUser(String userId, int n) {
        CriteriaQuery<VirtualMachine> criteriaQuery = criteriaQuery();
        CriteriaBuilder               builder       = currentSession().getCriteriaBuilder();
        Root<VirtualMachine>          from          = criteriaQuery.from(VirtualMachine.class);
        Join<VirtualMachine, VMRam>   ram           = from.join(Constants.VMConstants.RAM, JoinType.LEFT);
        Join<VirtualMachine, User>    user          = from.join(Constants.VMConstants.USER, JoinType.LEFT);
        criteriaQuery
                .select(from)
                .where(builder.equal(user.get(Constants.UsersConstants.EMAIL_ID), userId))
                .orderBy(builder.desc(ram.get(SIZE)));
        return currentSession().createQuery(criteriaQuery).setMaxResults(n).getResultList();
    }

    public List<VirtualMachine> getTopNVMs(int n) {
        CriteriaQuery<VirtualMachine> criteriaQuery = criteriaQuery();
        CriteriaBuilder               builder       = currentSession().getCriteriaBuilder();
        Root<VirtualMachine>          from          = criteriaQuery.from(VirtualMachine.class);
        Join<VirtualMachine, VMRam>   ram           = from.join(Constants.VMConstants.RAM, JoinType.LEFT);
        criteriaQuery
                .select(from)
                .orderBy(builder.desc(ram.get(SIZE)));
        return currentSession().createQuery(criteriaQuery).setMaxResults(n).getResultList();
    }

    public Optional<VirtualMachine> getByName(String name) {
        CriteriaQuery<VirtualMachine> criteriaQuery = criteriaQuery();
        CriteriaBuilder     builder       = currentSession().getCriteriaBuilder();
        Root<VirtualMachine>          from          = criteriaQuery.from(VirtualMachine.class);
        criteriaQuery.select(from).where(builder.equal(from.get(Constants.NAME), name));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
