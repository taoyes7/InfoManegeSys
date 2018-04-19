package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface DocDirRepository extends JpaRepository<DocDir, Long> {

    ArrayList<DocDir> findByParent(String parent);
    DocDir findFirstByPid(String pid);
    DocDir findFirstByName(String name);
    DocDir findFirstByLevelAndUser(int level, String user);
    DocDir findFirstByUserAndNameAndParent(String user,String name, String parent);
    DocDir findFirstByPidAndUser(String pid,String user);

    @Modifying(clearAutomatically = true)
    @Query("update DocDir d set d.label=?1 where d.user=?2 and d.pid=?3")
    void updateLabelByUserAndPid(String label, String user, String pid);

}
