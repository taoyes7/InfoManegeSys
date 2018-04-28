package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface DocDirRepository extends JpaRepository<DocDir, Long> {

    ArrayList<DocDir> findByParentAndStatus(String parent,String status);
    DocDir findFirstByPidAndStatus(String pid,String status);
    DocDir findFirstByNameAndStatus(String name,String status);
    DocDir findFirstByLevelAndUserAndStatus(int level, String user,String status);
    DocDir findFirstByUserAndNameAndParentAndStatus(String user,String name, String parent,String status);
    DocDir findFirstByPidAndUserAndStatus(String pid,String user,String status);

    @Modifying(clearAutomatically = true)
    @Query("update DocDir d set d.label=?1 where d.user=?2 and d.pid=?3")
    void updateLabelByUserAndPid(String label, String user, String pid);
    @Modifying(clearAutomatically = true)
    @Query("update DocDir d set d.label=?1 where  d.pid=?2")
    void updateLabelByPid(String label, String pid);
    @Modifying(clearAutomatically = true)
    @Query("update DocDir d set d.status=?1 where  d.pid=?2")
    void updateStatusByPid(String status, String pid);


}
