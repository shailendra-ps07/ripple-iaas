package com.ripple.iaas.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

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
public class UserRegistrationForm {
    @NonNull private String  name;
    @NonNull private String  email;
    @NonNull private String  mobileNo;
    @NonNull private String  password;
    @NonNull private boolean isMaster;
}
