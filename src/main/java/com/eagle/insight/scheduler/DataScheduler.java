package com.eagle.insight.scheduler;

import com.eagle.insight.scheduler.service.PARoleProcessor;
import com.eagle.insight.scheduler.service.UserRoleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataScheduler {

    @Autowired
    UserRoleProcessor userRoleProcessor;

    @Autowired
    PARoleProcessor paRoleProcessor;

    @Scheduled(fixedRate = 1000000)
    //@Scheduled(cron = "0/15 * * * * *")
    public void runJob() throws InterruptedException {
        userRoleProcessor.processUserRoleData();
        paRoleProcessor.processPAData();


    }
}
