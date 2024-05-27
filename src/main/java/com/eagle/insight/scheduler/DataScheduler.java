package com.eagle.insight.scheduler;

import com.eagle.insight.scheduler.model.UserRole;
import com.eagle.insight.scheduler.model.UserRoleMapping;
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

    @Scheduled(fixedRate = 1000000)
    public void dataTransferTask() throws InterruptedException {

        System.out.println("Running scheduler" + new Date());
        List<UserRole> userRoles = userRoleRepository.getAllUserRoles();
        System.out.println(userRoles.size());
        List<UserRoleMapping> userRoleMappingList = constructUserRoleMappings(userRoles);
        userRoleMappingRepository.saveAll(userRoleMappingList);
//        userRoleMappingRepository.
        System.out.println("All the records are successfully saved");

    }
//    public List<UserRole> getAllUsers() {
//        return employeeRepository.findAll();
//    }
    private List<UserRoleMapping> constructUserRoleMappings(List<UserRole> userRoles) {
        return userRoles.stream().map(userRole ->
                UserRoleMapping.builder()
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
