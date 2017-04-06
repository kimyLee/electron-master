package edu.scut.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/6/2.
 */
public interface GoodsMapper {
    int insert(Object obj) throws Exception;
    int update(Object obj) throws Exception;
    int delete(Object obj) throws Exception;
    List<Object> search(Object obj) throws Exception;
    List<Object> query(Object obj) throws Exception;
    int selectCount() throws Exception;
    Map<String, Object> selectByID(@Param("id") int id) throws Exception;
}
