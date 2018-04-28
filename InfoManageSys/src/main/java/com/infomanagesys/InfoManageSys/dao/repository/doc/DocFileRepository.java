package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface DocFileRepository extends JpaRepository<DocFile, Long> {
    DocFile findFirstById(Long id);
    ArrayList<DocFile> findByParentAndStatus(String parent,String status);
    DocFile findFirstByNameAndPostfixAndStatus(String name,String postfix,String status);
    DocFile findFirstByUserAndParentAndNameAndPostfixAndStatus(String user,String parent,String name,String postfix,String status);
    DocFile findFirstByPidAndStatus(String pid,String status);
    @Modifying(clearAutomatically = true)
    @Query("update DocFile d set d.status=?1 where d.pid=?2 ")
    void updateStatusByPid(String status, String pid);

}
