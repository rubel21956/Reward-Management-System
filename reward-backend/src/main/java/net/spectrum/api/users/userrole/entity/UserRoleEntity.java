package net.spectrum.api.users.userrole.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "userrole")
public class UserRoleEntity {
    @Id
    @Column(name = "loginoid", updatable = false, nullable = false)
    private String loginOid;

    @Column(name = "roleoid")
    private String roleOid;
}
