package edu.scut.controller;

import edu.scut.dao.CustomersMapper;
import edu.scut.dao.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.scut.controller.ObjectToMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weirilong on 2016/6/2.
 */

@Controller
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseBody
    public Object addCustomers(@RequestBody Map<String, Object> model) throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	String phone = model.get("phone").toString();
    	int cExist = customersMapper.judge(phone);
        int sExist = supplierMapper.queryPhone(phone);

    	if(cExist + sExist > 0){
    		result.put("ret", 2);
            result.put("msg", "该手机号码已被使用");
            return result;
    	}
        int success = customersMapper.insert(model);
        
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateCustomers(@RequestBody Map<String, Object> model) throws Exception {
    	Map<String, Object> judgeResult = new HashMap<String, Object>();
    	List<Object> objects = customersMapper.updateJudge(model);
    	if(!objects.isEmpty()){
    		judgeResult.put("ret", 2);
    		judgeResult.put("msg", "该手机号码已被使用");
            return judgeResult;
    	}
        customersMapper.update(model);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteCustomers(@RequestBody Map<String, Object> model) throws Exception {
        customersMapper.delete(model);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object searchCustomers(@RequestParam String key) throws Exception {
        List<Object> customers = customersMapper.search(key);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("customers", customers);
        return result;
    }


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCustomers(@RequestParam int pageNum, @RequestParam int pageSize) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startRow", (pageNum-1)*pageSize);
        param.put("size", pageSize);
        int total = customersMapper.selectCount();
        List<Object> customers = customersMapper.query(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("total", total);
        result.put("customers", customers);
        return result;
    }


    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    @ResponseBody
    public Object addDebit(@RequestBody Map<String, Object> model) throws Exception {
        customersMapper.insertDebit(model);
        int lastId = customersMapper.selectMaxId();
        Map<String, Object> old = customersMapper.selectDebitInfo(model);
        int type = (Integer) model.get("type");
        if(type == 0 && old == null) {
            customersMapper.insertDebitInfo(model);
        } else {
            double money = 0;
            if(model.get("money") instanceof Integer)
                money = (double)(Integer) model.get("money");
            else
                money = (Double) model.get("money");
            BigDecimal old_money = (BigDecimal) old.get("money");
            double loan = type == 0 ? money + old_money.doubleValue() : old_money.doubleValue() - money;
            old.put("money", loan);
            customersMapper.updateDebitInfo(old);
        }
        int cId = Integer.parseInt(model.get("cId").toString());
        customersMapper.updateCustomerLoan(cId);
//        int id = (Integer) model.get("cId");
//        int type = (Integer) model.get("type");
//        Double money = (Double) model.get("sum");
//        Map<String, Object> customer = customersMapper.selectByID(id);
//        BigDecimal old_loan = (BigDecimal) customer.get("loan");
//        double loan = type == 0 ? money + old_loan.doubleValue() : old_loan.doubleValue() - money;
//        customer.put("loan", loan);
//        customersMapper.update(customer);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("lastId", lastId);
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value = "/loandetails", method = RequestMethod.GET)
    @ResponseBody
    public Object loandetails(@RequestParam int id) throws Exception {
        List<Object> loan = customersMapper.queryLoanDetails(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("details", loan);
        return result;
    }
    @RequestMapping(value = "/loantotal", method = RequestMethod.GET)
    @ResponseBody
    public Object loantotal(HttpServletRequest request) throws Exception {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        List<Object> loan;
        // Map<Integer, Object> result;
        if((startDate == null||"".equals(startDate))){
            loan = customersMapper.queryLoanTotalToday();
        }else{
            loan = customersMapper.queryLoanTotalTodayBetween(startDate, endDate);
        }
        List<Object> loan2 = customersMapper.queryLoanTotal();
        for (int i = 0; i < loan.size(); i++) {
            System.out.print(loan.get(i));
            ObjectToMap test = (ObjectToMap)(loan.get(i));
            System.out.print(test.getId());
            // result.put(loan.get(i).get("id"))
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("total", loan);
        return result;
    }
    //借款还款历史记录
    @RequestMapping(value = "/loanhistory", method = RequestMethod.GET)
    @ResponseBody
    public Object loanHistory(HttpServletRequest request) throws Exception {
    	int id = Integer.parseInt(request.getParameter("id"));
    	List<Object> loan;
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	if(!(startDate == null||"".equals(startDate)) && !(endDate == null||"".equals(endDate))){
    		loan = customersMapper.queryLoanHistoryBetween(id,startDate,endDate);
    	}else{
    		loan = customersMapper.queryLoanHistory(id);
    	}
    	
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("details", loan);
        return result;
    }
    //借款还款所有历史记录
    @RequestMapping(value = "/loanhistorys", method = RequestMethod.GET)
    @ResponseBody
    public Object loanAllHistory(HttpServletRequest request) throws Exception {
//    	int id = Integer.parseInt(request.getParameter("id"));
    	List<Object> loan;
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	if(!(startDate == null || "".equals(startDate)) && !(endDate == null || "".equals(endDate))){
    		loan = customersMapper.queryLoanHistorysBetween(startDate,endDate);
    	}else{
    		loan = customersMapper.queryLoanHistorys();
    	}
    	
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("details", loan);
        return result;
    }
}
