package edu.scut.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by weirilong on 2016/5/25.
 */
@Repository
public interface CostMapper {

    int insertOrder(Object obj) throws Exception;

    int insertCost(Object obj) throws Exception;

    int updateCost(Object obj) throws Exception;

    int deleteCost(Object obj) throws Exception;

    List<Object> searchCost(Object obj) throws Exception;

    int selectCostCount() throws Exception;

    List<Object> queryCost(Object obj) throws Exception;

    List<Object> queryCostOrder(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("delete") String delete) throws Exception;

    int invalidCostOrder(Object obj) throws Exception;
}
