package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.User;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 30/04/19.
 */
public interface UserDao {

    Optional<User> getById(String emailId, String mobileNo);

    User registerUser(User user);

    void removeUser(User user);

}
