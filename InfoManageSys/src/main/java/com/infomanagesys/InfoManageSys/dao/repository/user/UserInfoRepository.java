package com.infomanagesys.InfoManageSys.dao.repository.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findFirstByUserId(String userId);
}
