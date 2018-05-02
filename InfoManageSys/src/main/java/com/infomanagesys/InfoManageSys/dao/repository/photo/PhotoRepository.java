package com.infomanagesys.InfoManageSys.dao.repository.photo;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("select p from Photo p where p.groupId=?1 and p.status = ?2 order by p.updateDate desc ")
    ArrayList<Photo> findByGroupIdAndStatus(String groupId,String status);

    Photo findFirstByPidAndStatus(String pid,String status);
}
