package com.ripple.iaas.db.entities;

import com.ripple.iaas.models.enums.UserAccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shailendra.ps
 * @since 16/07/19.
 */
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile_no")
})
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "mobile_no", unique = true)
    private String mobileNo;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "is_master")
    @Enumerated
    private UserAccountType userAccountType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    Set<VirtualMachine> virtualMachines = new HashSet<>();

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;
}
