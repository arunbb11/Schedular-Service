package com.eagle.insight.scheduler;

import com.eagle.insight.scheduler.model.PARole;
import com.eagle.insight.scheduler.model.UserRole;
import com.eagle.insight.scheduler.model.UserRoleMapping;
import com.eagle.insight.scheduler.repository.PARoleRepository;
import com.eagle.insight.scheduler.repository.UserRoleMappingRepository;
import com.eagle.insight.scheduler.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataScheduler {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    PARoleRepository paRoleRepository;

    @Scheduled(fixedRate = 1000000)
    //@Scheduled(cron = "0/15 * * * * *")
    public void add2DBJob() throws InterruptedException {

        System.out.println("Running scheduler" + new Date());
        List<UserRole> userRoles = userRoleRepository.getAllUserRoles();
        System.out.println("User Role  - " + userRoles.size());
//Query2
/*        List<PARole> paRole = paRoleRepository.getAllPARoles();
        System.out.println("PA Role  - "+ paRole.size());*/


        System.out.println("Fetching all records from UserRoleMappingTable");
        List<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findAll();
        System.out.println(userRoleMapping.size());
//Table1 - userRoles
// Table2 - userRoleMapping

        List<UserRole> modilfiedUserRoles = userRoles.stream()
                .filter(userRole -> isUserRoleChanged(userRole, userRoleMapping)).collect(Collectors.toList());



    /*     List<UserRoleMapping> userRoleMappingResult =

       //
         userRoles.stream()
                .filter(e -> !userRoleMapping.contains(e))
                .collect(Collectors.toList());

*/

        List<UserRoleMapping> userRoleMappingList = constructUserRoleMappings(userRoles);
        userRoleMappingRepository.saveAll(userRoleMappingList);
        System.out.println("All the records are successfully saved");

    }

    private boolean isUserRoleChanged(UserRole userRole, List<UserRoleMapping> userRoleMappings) {
        userRoleMappings.stream().filter(e -> e.getUserId().equals(userRole.getUserId())
        && (e.getRoleId().equalsIgnoreCase(userRole.getRoleId())
        || (e.getEndDate().equals(userRole.getEndDate()))
                )

        ).collect(Collectors.toList());

    }

    //    public List<UserRole> getAllUsers() {
//        return employeeRepository.findAll();
//    }
    private List<UserRoleMapping> constructUserRoleMappings(List<UserRole> userRoles) {
        return userRoles.stream().map(userRole ->
                        UserRoleMapping.builder()
/*                        //TODO - PA role
                        .userId(paRole.getUserId())
                        .roleId(paRole.getRoleId())*/
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
                                .build()
        ).collect(Collectors.toList());
    }
}
