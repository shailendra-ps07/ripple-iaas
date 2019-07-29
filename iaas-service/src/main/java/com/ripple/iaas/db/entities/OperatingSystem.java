package com.ripple.iaas.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@Entity(name = "operating_systems")
@Table(name = "operating_systems")
@Data
@NoArgsConstructor
public class OperatingSystem {

    @Id
    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "version")
    private String version;
}
