package com.infomanagesys.InfoManageSys.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    String selectNameById(Long Id);
}
