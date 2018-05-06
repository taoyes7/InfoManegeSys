package com.infomanagesys.InfoManageSys.dao.repository.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UserDiaryRepository extends JpaRepository<UserDiary, Long> {
    @Query("select u from UserDiary u where u.userId=?1 and u.status=?2  order by u.createDate desc ")
    ArrayList<UserDiary> findByUserIdAndStatus(String userId,String status);
    UserDiary findFirstByPidAndStatus(String pid,String status);

}
