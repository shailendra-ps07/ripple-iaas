package com.ripple.iaas.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Entity(name = "VirtualMachine")
@Table(name = "virtual_machines", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Getter
@Setter
@NoArgsConstructor
public class VirtualMachine {

    @Id
    @NotNull
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name="operating_system")
    private OperatingSystem operatingSystem;

    @OneToOne
    @JoinColumn(name="ram")
    @OrderBy("size Desc") // sorting in desc order of ram size
    private VMRam ram;

    @OneToOne
    @JoinColumn(name="cpu_cores")
    private VMCpuCores cpuCores;

    @OneToOne
    @JoinColumn(name="hard_disk")
    private VMHardDisk hardDisk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

}
