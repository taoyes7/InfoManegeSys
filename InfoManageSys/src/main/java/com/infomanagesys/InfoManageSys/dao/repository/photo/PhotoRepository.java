package com.infomanagesys.InfoManageSys.dao.repository.photo;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
