package com.infomanagesys.InfoManageSys.dao.repository.doc;

import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findFirstByContent(String content);
    ArrayList<Label> findByUser(String user);
    Label findFirstByPid(String pid);
}
