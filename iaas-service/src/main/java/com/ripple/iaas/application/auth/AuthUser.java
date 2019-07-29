package com.ripple.iaas.application.auth;

import lombok.Builder;
import lombok.Data;

import java.security.Principal;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Data
@Builder
public class AuthUser implements Principal {

    private final String name;

    private final String role;
}
