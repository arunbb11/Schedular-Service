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
@Table(name = "PA0001", schema = "RFC")
public class PARole {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_NAME")
    private String roleId;
}
