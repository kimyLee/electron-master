package edu.scut.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Repository
public interface SalesMapper {
    int delete(Object obj) throws Exception;

    int insert(Object obj)throws Exception;

    int insertStock(Object obj)throws Exception;

    Object selectById(@Param("id")int id)throws Exception;

    List<Object> selectByPage(int delete) throws Exception;

    int updateByPrimaryKeySelective(Object obj)throws Exception;

    List<Object> selectByDate(@Param("startDate") String startDate, @Param("endDate")String endDate, @Param("delete")String isDelete) throws Exception;

    List<Map<String,Object>> selectDetail(@Param("id") int id, @Param("delete")int isDelete);

    List<Object> selectGoodsByDate(@Param("startDate") String startDate, @Param("endDate")String endDate, @Param("supplier")String supplier) throws Exception;

    List<Object> selectGoodsIDByDate(@Param("startDate") String startDate, @Param("endDate")String endDate, @Param("id")int id) throws Exception;

	/**
	 * @return
	 */
	int selectMaxId()throws Exception;

    Map<String,Object> selectCustomerOwnCompany(@Param("cId")int customerId, @Param("supplier")String supplier);

    int deleteDebitBySale(int id);

    List<Object> selectDeleteByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);
}
