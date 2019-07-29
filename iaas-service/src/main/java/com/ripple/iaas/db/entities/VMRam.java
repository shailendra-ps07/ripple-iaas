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
@Entity(name = "rams")
@Table(name = "rams")
@Data
@NoArgsConstructor
public class VMRam {

    @Id
    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "size")
    private Integer size;
}
