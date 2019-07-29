package com.ripple.iaas.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shailendra.ps
 * @since 29/07/19.
 */
@Entity(name = "UserAuth")
@Table(name = "user_auth", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id"),
        @UniqueConstraint(columnNames = "access_token_id")
})
@Getter
@Setter
@NoArgsConstructor
public class AuthToken {

    @Id
    @NotNull
    @Column(name = "access_token_id", unique = true)
    private String accessTokenId;

    @NotNull
    @Column(name="user_id")
    private String userId;

    @NotNull
    @Column(name = "last_access_utc", unique = true)
    private DateTime lastAccessUTC;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

    public AuthToken(String accessTokenId, String userId, DateTime lastAccessUTC) {
        this.accessTokenId = accessTokenId;
        this.userId = userId;
        this.lastAccessUTC = lastAccessUTC;
    }
}
