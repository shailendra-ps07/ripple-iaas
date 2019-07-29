package com.ripple.iaas.models.request;

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
@AllArgsConstructor
@Builder
public class VMRequestForm {

    @NonNull private String name;
    @NonNull private String operatingSystem;
    @NonNull private String ram;
    @NonNull private String hardDisk;
    @NonNull private String cpuCores;

}
