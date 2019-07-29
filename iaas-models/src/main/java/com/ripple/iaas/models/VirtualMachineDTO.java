package com.ripple.iaas.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualMachineDTO {

    private String name;

    private OperatingSystemDTO operatingSystem;

    private VMRamDTO ram;

    private VMCpuCoresDTO cpuCores;

    private VMHardDiskDTO hardDisk;

}
