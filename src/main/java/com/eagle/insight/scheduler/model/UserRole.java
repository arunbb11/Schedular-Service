package com.eagle.insight.scheduler.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ZPS_PROJ_STAKE", schema = "RFC")
public class UserRole {

//    user_id =  user_id
//    role_id = role_name
//    end_date = valid_to
//    String userId;
//    String roleId;
//    String endDate;

//We need unique column as JPA is not working properly
    @Id
    @Column(name = "DB_KEY")
    private String dbKey;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_NAME")
    private String roleId;

    @Column(name = "VALID_TO")
    private String endDate;



}
