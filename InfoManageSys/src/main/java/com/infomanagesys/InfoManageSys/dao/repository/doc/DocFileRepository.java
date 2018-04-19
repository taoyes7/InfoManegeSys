package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DocFileRepository extends JpaRepository<DocFile, Long> {
    DocFile findFirstById(Long id);
    ArrayList<DocFile> findByParent(String parent);
    DocFile findFirstByNameAndPostfix(String name,String postfix);
    DocFile findFirstByUserAndParentAndNameAndPostfix(String user,String parent,String name,String postfix);
}
