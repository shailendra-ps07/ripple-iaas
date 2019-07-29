package com.ripple.iaas.application.auth;

import com.google.inject.Singleton;
import com.ripple.iaas.models.enums.UserAccountType;
import io.dropwizard.auth.Authorizer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Singleton
public class AppAuthorizer implements Authorizer<AuthUser> {

    public static Map<String, UserAccountType> accountTypeTable = new HashMap<>();

    @Override
    public boolean authorize(AuthUser user, String role) {
        if(accountTypeTable.containsKey(user.getName()) &&
                role.equals(accountTypeTable.get(user.getName()))) {
            return true;
        }
        return false;
    }
}
