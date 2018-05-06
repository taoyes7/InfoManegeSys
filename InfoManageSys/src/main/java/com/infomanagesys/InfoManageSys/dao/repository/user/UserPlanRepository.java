package com.infomanagesys.InfoManageSys.dao.repository.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    ArrayList<UserPlan> findByUserIdAndStatus(String userId, String status);
    ArrayList<UserPlan> findByUserIdAndStatusAndIsEndOrderByLevelAsc(String userId, String status,String isEnd);
    ArrayList<UserPlan> findByUserIdAndStatusAndIsEndOrderByCreateDateDesc(String userId, String status,String isEnd);
    UserPlan findFirstByPidAndStatus(String pid,String status);
}
