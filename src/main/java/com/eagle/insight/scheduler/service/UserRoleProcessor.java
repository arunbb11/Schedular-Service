package com.eagle.insight.scheduler.service;

import com.eagle.insight.scheduler.model.UserRole;
import com.eagle.insight.scheduler.model.UserRoleMapping;
import com.eagle.insight.scheduler.repository.PARoleRepository;
import com.eagle.insight.scheduler.repository.UserRoleMappingRepository;
import com.eagle.insight.scheduler.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleProcessor {

    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    PARoleRepository paRoleRepository;

    public void processUserRoleData() throws InterruptedException {

        System.out.println("Running scheduler" + new Date());
        List<UserRole> userRoles = userRoleRepository.getAllUserRoles();
        System.out.println("User Role  - " + userRoles.size());

        System.out.println("Fetching all records from UserRoleMappingTable");
        List<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findAll();
        System.out.println(userRoleMapping.size());

        //T2 record absent in T1 then setting active flag to 0
        List<UserRoleMapping> inactivatedRoles = userRoleMapping.stream()
                .filter(urm -> checkIfAbsent(urm, userRoles))
                .map(urm -> {
                    urm.setActiveFlag(0);
                    return urm;
                })
                .collect(Collectors.toList());
        userRoleMappingRepository.saveAll(inactivatedRoles);

        // Check if record already exists in T2, if exists? check if End date is same : consider as new record and save it
        for (UserRole userRole : userRoles) {
            UserRoleMapping userRoleMapping1 = checkIfPresent(userRole, userRoleMapping);
            if (userRoleMapping1 != null) {
                if (!userRole.getEndDate().equalsIgnoreCase(userRoleMapping1.getEndDate().replaceAll("-", ""))) {
                    System.out.println("userRole.getEndDate() : " + userRole.getEndDate() + " userRoleMapping1.getEndDate()" + userRoleMapping1.getEndDate());
                    //update if enddate is changed
                    userRoleMappingRepository.save(userRoleMapping1);
                    System.out.println("User Role Mapping updated : " + userRoleMapping1);
                }
                // do nothing, if userId, roleId and end date are same
            } else {
                // create new row, if userRoleMapping is absent
                UserRoleMapping userRoleMapping2 = constructUserRoleMapping(userRole);
                userRoleMappingRepository.save(userRoleMapping2);
                System.out.println("User Role Mapping inserted : " + userRoleMapping2);
            }
        }
        System.out.println("All the Records migrated successfully");
    }

    private boolean checkIfAbsent(UserRoleMapping urm, List<UserRole> userRoles) {
        return userRoles.stream().noneMatch(e -> e.getUserId().equalsIgnoreCase(urm.getUserId()) &&
                e.getRoleId().equalsIgnoreCase(urm.getRoleId()));
    }

    private UserRoleMapping checkIfPresent(UserRole userRole, List<UserRoleMapping> userRoleMapping) {
        return userRoleMapping.stream().filter(e -> e.getUserId().equalsIgnoreCase(userRole.getUserId()) &&
                e.getRoleId().equalsIgnoreCase(userRole.getRoleId())).findAny().orElse(null);
    }

    private UserRoleMapping constructUserRoleMapping(UserRole userRole) {
        return UserRoleMapping.builder()
                .userId(userRole.getUserId())
                .roleId(userRole.getRoleId())
                .effectiveDate(new Date())
                .endDate(userRole.getEndDate())
                .activeFlag(1)
                .createdOn(new Date())
                //TODO -
                .createdBy("sysUser")
                .changedOn(null)
                .changedBy(null)
                .build();
    }
}
