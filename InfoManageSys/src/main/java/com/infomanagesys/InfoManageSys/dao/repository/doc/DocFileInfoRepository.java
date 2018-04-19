package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocFileInfoRepository extends JpaRepository<DocFileInfo, Long> {
    DocFileInfo findFirstByFileId(String fileId);
}
