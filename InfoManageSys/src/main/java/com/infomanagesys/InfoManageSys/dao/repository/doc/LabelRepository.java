package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findFirstByContent(String content);
    ArrayList<Label> findByUser(String user);
    Label findFirstByPid(String pid);
    Label findFirstByUserAndPid(String user,String pid);
    Label findFirstByUserAndContent(String user,String content);
    @Modifying
    @Query("update Label l set l.type=?1 where l.pid=?2")
    void updateTypeByPid(String type,String pid);
    void deleteByPid(String pid);
}
