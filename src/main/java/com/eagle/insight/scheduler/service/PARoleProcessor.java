package com.eagle.insight.scheduler.service;

import com.eagle.insight.scheduler.model.PARole;
import com.eagle.insight.scheduler.model.UserRoleMapping;
import com.eagle.insight.scheduler.repository.PARoleRepository;
import com.eagle.insight.scheduler.repository.UserRoleMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PARoleProcessor {


    @Autowired
    PARoleRepository paRoleRepository;
    @Autowired
    UserRoleMappingRepository userRoleMappingRepository;

    public void processPAData() throws InterruptedException {

        System.out.println("Running scheduler" + new Date());
        List<PARole> paRoles = paRoleRepository.getAllPARoles();
        System.out.println("PA Role  - " + paRoles.size());

        System.out.println("Fetching all records from UserRoleMappingTable");
        List<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findAll();
        System.out.println(userRoleMapping.size());

        //T2 record absent in T1 then setting active flag to 0
        List<UserRoleMapping> inactivatedRoles = userRoleMapping.stream()
                .filter(urm -> checkIfAbsent(urm, paRoles))
                .map(urm -> {
                    urm.setActiveFlag(0);
                    return urm;
                })
                .collect(Collectors.toList());
        userRoleMappingRepository.saveAll(inactivatedRoles);

        // Check if record already exists in T2, if exists? check if End date is same : consider as new record and save it
        for (PARole paRole : paRoles) {
            UserRoleMapping userRoleMapping1 = checkIfPresent(paRole, userRoleMapping);
            if (userRoleMapping1 == null) {
                // create new row, if userRoleMapping is absent
                UserRoleMapping userRoleMapping2 = constructUserRoleMapping(paRole);
                userRoleMappingRepository.save(userRoleMapping2);
                System.out.println("User Role Mapping inserted : " + userRoleMapping2);
            }
        }
        System.out.println("All the Records migrated successfully");
    }

    private boolean checkIfAbsent(UserRoleMapping urm, List<PARole> paRoles) {
        return paRoles.stream().noneMatch(e -> e.getUserId().equalsIgnoreCase(urm.getUserId()) &&
                e.getRoleId().equalsIgnoreCase(urm.getRoleId()));
    }

    private UserRoleMapping checkIfPresent(PARole paRole, List<UserRoleMapping> userRoleMapping) {
        return userRoleMapping.stream().filter(e -> e.getUserId().equalsIgnoreCase(paRole.getUserId()) &&
                e.getRoleId().equalsIgnoreCase(paRole.getRoleId())).findAny().orElse(null);
    }

    private UserRoleMapping constructUserRoleMapping(PARole paRole) {
        return UserRoleMapping.builder()
                .userId(paRole.getUserId())
                .roleId(paRole.getRoleId())
                .effectiveDate(new Date())
                .endDate("9999-12-31")
                .activeFlag(1)
                .createdOn(new Date())
                //TODO -
                .createdBy("sysUser")
                .changedOn(null)
                .changedBy(null)
                .build();
    }
}
