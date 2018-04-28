package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LabelTypeRepository extends JpaRepository<LabelType, Long> {
    ArrayList<LabelType> findByUser(String user);
    LabelType findFirstByUserAndName(String user,String name);
    void deleteByPid(String pid);
}
