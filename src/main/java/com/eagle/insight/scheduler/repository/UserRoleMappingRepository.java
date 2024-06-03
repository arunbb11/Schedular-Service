package com.eagle.insight.scheduler.repository;

import com.eagle.insight.scheduler.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, String> {
}
