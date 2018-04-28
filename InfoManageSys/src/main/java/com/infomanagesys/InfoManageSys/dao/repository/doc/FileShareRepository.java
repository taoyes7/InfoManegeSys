package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    FileShare findFirstByPidAndStatus(String pid,String status);
}
