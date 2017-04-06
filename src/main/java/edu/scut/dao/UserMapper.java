package edu.scut.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by weirilong on 2016/5/25.
 */
@Repository
public interface UserMapper {

    int insert(Object obj) throws Exception;
    String selectByUser(@Param("username") String username) throws Exception;
    int update(Object obj) throws Exception;
}
