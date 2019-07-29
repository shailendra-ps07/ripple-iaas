package com.ripple.iaas.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ripple.iaas.models.enums.UserAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class UserDTO {

    private String name;

    private String email;

    private String mobileNo;

    private String password;

    private UserAccountType userAccountType;

    Set<VirtualMachineDTO> virtualMachines = new HashSet<>();
}
