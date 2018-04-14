package com.infomanagesys.InfoManageSys.dao.repository;

import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TempTableRepository extends JpaRepository<TempTable, Long>{
    TempTable findFirstByTempKey(String tempKey);
    TempTable findFirstByTempUserAndTempType(String tempUser,String tempType);

    @Modifying(clearAutomatically = true)
    @Query("update TempTable t set t.tempValue=?1 where t.tempUser=?2 and t.tempType=?3")
    void updateValue(String tempValue, String tempUser,String tempType);




}
