package edu.scut.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/6/2.
 */
public interface CustomersMapper {
    int insert(Object obj) throws Exception;
    int update(Object obj) throws Exception;
    int delete(Object obj) throws Exception;
    List<Object> search(Object obj) throws Exception;
    List<Object> query(Object obj) throws Exception;
    int selectCount() throws Exception;
    int insertDebit(Object obj) throws Exception;
    int insertDebitBySale(Object obj) throws Exception;
    double selectLoan(@Param("id") int id) throws Exception;
    Map<String, Object> selectByID(@Param("id") int id) throws Exception;
    Map<String, Object> selectDebitInfo(Object obj) throws Exception;
    int insertDebitInfo(Object obj) throws Exception;
    int updateDebitInfo(Object obj) throws Exception;
    List<Object> queryLoanDetails(@Param("id") int id) throws Exception;
    List<Object> queryLoanTotal() throws Exception;
	/**
	 * @param id
	 * @return loan history 
	 */
	List<Object> queryLoanHistory(int id);
	/**
	 * @param phone
	 */
	int judge(String phone);
	/**
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object> queryLoanHistoryBetween(@Param("id")int id, @Param("startDate")String startDate, @Param("endDate")String endDate);
	List<Object> queryLoanTotalToday();
	List<Object> queryLoanTotalTodayBetween(@Param("startDate")String startDate, @Param("endDate")String endDate);
	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object> queryLoanHistorysBetween(@Param("startDate")String startDate, @Param("endDate")String endDate);
	/**
	 * @return
	 */
	List<Object> queryLoanHistorys();
	/**
	 * @param model
	 * @return
	 */
	List<Object> updateJudge(Map<String, Object> model);
	
	int updateCustomerLoan(@Param("id")int id);
	/**
	 * 
	 */
	int selectMaxId()throws Exception;
}
