package com.eagle.insight.scheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERROLEMAPPING", schema = "POWERAPP_AUTH_SCHEMA")
public class UserRoleMapping {

/*
user_id
role_id
effective_date
end_date
active_flag
created_by
created_by
changed_on
changed_by*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    @Column(name = "END_DATE")
    private String endDate;

    @Column(name = "ACTIVE_FLAG")
    private Integer activeFlag;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CHANGED_ON")
    private Date changedOn;

    @Column(name = "CHANGED_BY")
    private String changedBy;

}
