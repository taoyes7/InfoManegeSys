package com.infomanagesys.InfoManageSys.dao.repository.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findFirstByAccountidAndPassworld(String accountid,String passworld);
    User findFirstByAccountid(String accountid);
}
