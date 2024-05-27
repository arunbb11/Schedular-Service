package com.eagle.insight.scheduler.repository;

import com.eagle.insight.scheduler.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    String query = "select distinct(stakeholder_id) as user_id,stakeholder_role,\n" +
            "\n" +
            "  case \n" +
            "\n" +
            "  when stakeholder_role = 01 then 'Role_ENGAGEMENT_MGR'\n" +
            "\n" +
            "  when stakeholder_role = 02 then 'Role_BUSINESS_LEAD'\n" +
            "\n" +
            "  when stakeholder_role = 03 then 'Role_PROG_MGR'\n" +
            "\n" +
            "  when stakeholder_role = 04 then 'Role_DELIVERY_MGR'\n" +
            "\n" +
            "  when stakeholder_role = 05 then 'Role_PROJECT_MGR'\n" +
            "\n" +
            "  when stakeholder_role = 06 then 'Role_SQAR'\n" +
            "\n" +
            "  when stakeholder_role = 14 then 'Role_BE_LEAD'\n" +
            "\n" +
            "  END as role_name, VALID_TO\n" +
            "\n" +
            "  from RFC.ZPS_PROJ_STAKE ps where valid_to >= GETDATE() and STAKEHOLDER_ROLE in (01,02,03,04,05,06,14) \n" +
            "\n" +
            "  and ps.valid_to = (select max(valid_to) from RFC.ZPS_PROJ_STAKE ps1 where ps.STAKEHOLDER_ID = ps1.STAKEHOLDER_ID\n" +
            "\n" +
            "  and ps.STAKEHOLDER_ROLE = ps1.STAKEHOLDER_ROLE) \n" +
            "\n" +
            "  order by user_id, STAKEHOLDER_ROLE";
    @Query(value = query, nativeQuery = true)
    public List<UserRole> getAllUserRoles();
}

