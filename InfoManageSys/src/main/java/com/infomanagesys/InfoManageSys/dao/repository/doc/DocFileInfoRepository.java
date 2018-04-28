package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocFileInfoRepository extends JpaRepository<DocFileInfo, Long> {
    DocFileInfo findFirstByFileId(String fileId);
    @Modifying(clearAutomatically = true)
    @Query("update DocFileInfo d set d.label=?1 where d.fileId=?2")
    void updateLabelByFileid(String label, String fileId);
}
