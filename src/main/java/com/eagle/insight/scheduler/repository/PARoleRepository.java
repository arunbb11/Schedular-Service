package com.eagle.insight.scheduler.repository;

import com.eagle.insight.scheduler.model.PARole;
import com.eagle.insight.scheduler.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PARoleRepository extends JpaRepository<PARole, String> {
    String query = "select distinct PA.PERNR as user_id,\n" +
            "\n" +
            "case \n" +
            "\n" +
            "when HR1003.ZZVBU = 'X' then 'Role_VBU_HEAD'\n" +
            "\n" +
            "when HR1003.ZPRACTICE= 'X' then 'Role_PRACTICE_HEAD'\n" +
            "\n" +
            "when ((HR1003.ZSUBVERTICAL= 'X') OR (HR1003.ZZSV = 'X')) then 'Role_SUBVERTICAL_HEAD'\n" +
            "\n" +
            "when HR1003.ZZHBU= 'X' then 'Role_HBU_HEAD'\n" +
            "\n" +
            "when HR1003.ZZGEO= 'X' then 'Role_GEO_HEAD'\n" +
            "\n" +
            "when ((HR1003.ZZFUNCTION2 = 'X') OR (HR1003.ZZFUNCTION = 'X')) then 'Role_FUNCTION_HEAD'\n" +
            "\n" +
            "when HR1003.ZZSUB_FUNCTION= 'X' then 'Role_SUBFUNCTION_HEAD'\n" +
            "\n" +
            "END as role_name\n" +
            "\n" +
            "from RFC.PA0001 as PA \n" +
            "\n" +
            ",RFC.HRP1001 as HR1001\n" +
            "\n" +
            ",RFC.HRP1003 as HR1003\n" +
            "\n" +
            ",RFC.HRP1000\tas HR1000\n" +
            "\n" +
            "where\n" +
            "\n" +
            "HR1001.RSIGN = 'B' and\n" +
            "\n" +
            "HR1001.RELAT = '012' and\n" +
            "\n" +
            "HR1001.ENDDA = '99991231' and\n" +
            "\n" +
            "HR1001.OBJID = HR1003.OBJID and\n" +
            "\n" +
            "HR1003.OBJID = HR1000.OBJID and\n" +
            "\n" +
            "HR1000.OTYPE = 'O' and\n" +
            "\n" +
            "PA.PLANS = HR1001.SOBID and\n" +
            "\n" +
            "PA.ENDDA = '99991231' and\n" +
            "\n" +
            "HR1003.ENDDA = '99991231' and\n" +
            "\n" +
            "(HR1003.ZZVBU = 'X' OR HR1003.ZPRACTICE= 'X' OR HR1003.ZSUBVERTICAL= 'X'\n" +
            "\n" +
            "OR HR1003.ZZSV = 'X' OR HR1003.ZZHBU= 'X' OR HR1003.ZZFUNCTION2 = 'X'\n" +
            "\n" +
            "OR HR1003.ZZFUNCTION = 'X' OR HR1003.ZZSUB_FUNCTION= 'X' )\n" +
            "\n" +
            "order by PA.PERNR\n";
    @Query(value = query, nativeQuery = true)
    public List<PARole> getAllPARoles();
}

