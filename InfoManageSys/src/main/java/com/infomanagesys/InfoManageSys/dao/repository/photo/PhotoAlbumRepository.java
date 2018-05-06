package com.infomanagesys.InfoManageSys.dao.repository.photo;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Long> {
    PhotoAlbum findFirstByUserAndNameAndStatusAndParentId(String user,String name,String status,String parentId);
    PhotoAlbum findFirstByPidAndStatus(String pid,String status);
    int countAllByParentId(String parentId);
    PhotoAlbum findFirstByUserAndStatusAndParentId(String userId,String status, String parentId);

    @Query("select p from PhotoAlbum p where p.parentId=?1 and p.status=?2 order by p.level asc ")
    ArrayList<PhotoAlbum> findByParentIdAndStatus(String parentId, String status);
    @Modifying
    @Query("update PhotoAlbum p set p.status=?1 where p.pid=?2")
    void updateStatusByPid(String status,String pid);

}
