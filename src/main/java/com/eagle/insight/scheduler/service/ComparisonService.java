package com.eagle.insight.scheduler.service;

import com.eagle.insight.scheduler.model.UserRole;
import com.eagle.insight.scheduler.model.UserRoleMapping;
import com.eagle.insight.scheduler.repository.UserRoleMappingRepository;
import com.eagle.insight.scheduler.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComparisonService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRoleMappingRepository userRoleMappingRepository;


   /* public void compareTables(){

        //Table1
        List<UserRole> table1Data = UserRoleRepository.findAll();

        //table 2
        List<UserRoleMapping> table2Data = UserRoleMappingRepository.findAll();
    }*/
}
