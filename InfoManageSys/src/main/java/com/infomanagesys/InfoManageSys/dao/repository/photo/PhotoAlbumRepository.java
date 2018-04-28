package com.infomanagesys.InfoManageSys.dao.repository.photo;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Long> {
}
