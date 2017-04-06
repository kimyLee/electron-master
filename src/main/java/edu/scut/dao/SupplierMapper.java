package edu.scut.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by weirilong on 2016/6/17.
 */
public interface SupplierMapper {
    int insert(Object obj) throws Exception;
    int delete(Object obj) throws Exception;
    int update(Object obj) throws Exception;
    int myUpdate(Object obj) throws Exception;
    int judgeAmount() throws Exception;
    int addAmount() throws Exception;
    List<Object> query() throws Exception;
    int insertTransfer(Object obj) throws Exception;
    List<Object> queryTransfer(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    int updateAmount(Object obj) throws Exception;
    Object selectAmount() throws Exception;
    int updateSettlement(Object obj) throws Exception;
    List<Object> selectSettlement() throws Exception;
    int invalidSettlement(@Param("id")int id) throws Exception;

//    List<Object> queryLoanHistorysBetween(@Param("name")String name,@Param("startDate") String startDate,
//                                          @Param("endDate") String endDate) throws Exception;
    List<Object> queryLoanHistorys(@Param("name")String name,@Param("startDate") String startDate,
            @Param("endDate") String endDate)throws Exception;

    int querySupplier(@Param("name")String name);

    int queryPhone(String phone);
}
