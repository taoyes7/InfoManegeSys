package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DocDirRepository extends JpaRepository<DocDir, Long> {

    ArrayList<DocDir> findByParent(String parent);
    DocDir findFirstByPid(String pid);
    DocDir findFirstByName(String name);
    DocDir findFirstByLevelAndUser(int level, String user);

}
