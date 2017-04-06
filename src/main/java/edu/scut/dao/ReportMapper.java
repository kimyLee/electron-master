package edu.scut.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Repository
public interface ReportMapper {
	List<Object> reportDetail(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    List<Object> selectSuppliers(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    List<Object> selectGoods(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectCostByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectTichengByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Map<String, Object> selectSaleByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectStockByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectTransferByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectAmount(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectRiqian(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectZongqian(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectHuankuan(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;

    Object selectBuhuo(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectShishou(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectjiekuan(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectSanlunByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectChunchangByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectShouru(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
    Object selectZhichu(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;



    List<Object> selectSanlun() throws Exception;
    List<Object> selectCost() throws Exception;
}
