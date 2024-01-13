package net.spectrum.api.officesection.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "officesection")
public class OfficeSectionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String oid;

    @Column(name = "officeoid")
    private String officeOid;

    @Column(name = "name")
    private String name;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdOn;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedOn;
}
