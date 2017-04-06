package edu.scut.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/6/17.
 */
public interface StockMapper {
    int insert(Object obj) throws Exception;
    List<Object> query(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") String type, @Param("supplier") String supplier) throws Exception;
    List<Object> queryDetail(@Param("id") int id, @Param("delete")int delete)throws Exception;
    List<Map<String, Object>> selectSettlement() throws Exception;
    int insertTichengs(@Param("list") List<Map<String, Object>> list) throws Exception;
    int selectInventory(@Param("id") int id) throws Exception;
    int updateInventory(Object obj) throws Exception;
    int insertHuocha(Object obj) throws Exception;
}
