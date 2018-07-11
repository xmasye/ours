package com.xmasye.ours.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface TestDao {

    void insert(@Param("value") int value);

}
