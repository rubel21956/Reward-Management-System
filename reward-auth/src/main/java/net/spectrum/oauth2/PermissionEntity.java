package net.spectrum.oauth2;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="Permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="permissionoid", updatable = false, nullable = false)
    private String permissionOid;

    @Column(name="permissionname")
    private String permissionName;

    @Column(name="topmenuid")
    private String topMenuId;

    @Column(name="leftmenuid")
    private String leftMenuId;

    @Column(name="createdon")
    private Timestamp createdOn;

}
