package com.infomanagesys.InfoManageSys.dao.repository.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {
    UserLink findFirstByPidAndStatus(String pid,String status);
    ArrayList<UserLink> findByUserIdAndStatus(String userId,String status);
}
