package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LabelGroupRepository extends JpaRepository<LabelGroup, Long> {
    LabelGroup findFirstByPid(String pid);
    void deleteByPid(String pid);
    @Modifying
    @Query("update LabelGroup l set l.labels=?1 where l.pid=?2")
    void updateLabelsByPid(String labels,String pid);
    @Modifying
    @Query("update LabelGroup l set l.fileType=?1 where l.pid=?2")
    void updateFileTypesByPid(String fileType,String pid);
    @Modifying
    @Query("update LabelGroup l set l.priorityLevel=?1 where l.pid=?2")
    void updateLevelByPid(int level,String pid);
}
