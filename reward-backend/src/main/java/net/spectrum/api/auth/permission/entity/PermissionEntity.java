package net.spectrum.api.auth.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="permissionoid", updatable = false, nullable = false)
    private  String permissionOid;

    @Column(name = "permissionname")
    private String permissionName;

    @Column(name = "url")
    private String url;

    @Column(name = "method")
    private String method;

    @Column(name = "topmenuid")
    private String topMenuId;

    @Column(name = "leftmenuid")
    private String leftMenuId;
    
    @CreationTimestamp
    @Column(name = "createdon")
    private Timestamp createdOn;
}
