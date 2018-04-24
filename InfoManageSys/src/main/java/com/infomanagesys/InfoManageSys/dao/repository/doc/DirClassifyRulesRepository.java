package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.label.DirClassifyRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DirClassifyRulesRepository extends JpaRepository<DirClassifyRules, Long> {
    DirClassifyRules findFirstByPid(String pid);
    @Modifying
    @Query("update DirClassifyRules d set d.labelsGroup=?1 where d.pid=?2")
    void updateLabelsGroupByPid(String labelsGroup, String pid);

}
