package edu.scut.controller;

import edu.scut.dao.CustomersMapper;
import edu.scut.dao.StockMapper;
import edu.scut.dao.SupplierMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private CustomersMapper customersMapper;


    @RequestMapping(value = "/supplier", method = RequestMethod.POST)
    @ResponseBody
    public Object addSupplier(@RequestBody Map<String, Object> model) throws Exception {

        Map<String, Object> resut = new HashMap<String, Object>();
        String name = model.get("name").toString();
        String phone = model.get("phone").toString();
        int exist = supplierMapper.querySupplier(name);

        int cExist = customersMapper.judge(phone);
        int sExist = supplierMapper.queryPhone(phone);

        if(exist > 0){
            resut.put("ret", -1);
            resut.put("msg", "供应商已存在");
        }else if(cExist + sExist > 0){
            resut.put("ret", 2);
            resut.put("msg", "电话号码已被使用");
        } else{
            supplierMapper.insert(model);
            resut.put("ret", 0);
            resut.put("msg", "请求已处理");
        }
//        resut.put("id", id);
        return resut;
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateSupplier(@RequestBody Map<String, Object> model) throws Exception {
        if(model.get("phone") == null ) {
            supplierMapper.myUpdate(model);
        }else{
            supplierMapper.update(model);
        }
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteSupplier(@RequestBody Map<String, Object> model) throws Exception {
        supplierMapper.delete(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.GET)
    @ResponseBody
    public Object query() throws Exception {
        List<Object> suppliers = supplierMapper.query();
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "");
        resut.put("suppliers", suppliers);
        return resut;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public Object addTransfer(@RequestBody Map<String, Object> model) throws Exception {
        int id = supplierMapper.insertTransfer(model);
        double amount = 0;
        if(model.get("amount") instanceof Integer)
            amount = (double) (Integer) model.get("amount");
        else
            amount = (Double) model.get("amount");
        BigDecimal oldAmount = (BigDecimal) supplierMapper.selectAmount();
        supplierMapper.updateAmount(oldAmount.doubleValue() - amount);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        //resut.put("id", id);
        return resut;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    @ResponseBody
    public Object queryTransfer(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        List<Object> transfers = supplierMapper.queryTransfer(startDate, endDate);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        resut.put("transfers", transfers);
        return resut;
    }

    @RequestMapping(value = "/amount", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateAmount(@RequestBody Object amount) throws Exception {
        supplierMapper.updateAmount(amount);

        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/amount", method = RequestMethod.GET)
    @ResponseBody
    public Object queryAmount() throws Exception {
        int num = supplierMapper.judgeAmount();
        BigDecimal amount;
        if(num>0){
            amount = (BigDecimal) supplierMapper.selectAmount();
        }else{
            supplierMapper.addAmount();
            amount = (BigDecimal) supplierMapper.selectAmount();
        }
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("amount", amount.doubleValue());
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/settlement", method = RequestMethod.POST)
    @ResponseBody
    public Object addSettlement(@RequestBody Map<String, Object> model) throws Exception {
        supplierMapper.updateSettlement(model);

        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/settlement", method = RequestMethod.GET)
    @ResponseBody
    public Object querySettlement() throws Exception {
        List<Object> settlements = supplierMapper.selectSettlement();

        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("settlements", settlements);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/settlement", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteSettlement(@RequestBody int id) throws Exception {
        supplierMapper.invalidSettlement(id);

        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "loanhistorys",method = RequestMethod.GET)
    @ResponseBody
    public Object getLoanHistorys(@RequestParam(value = "name", required = false, defaultValue="") String name,
                                  @RequestParam(value = "startDate", required = false, defaultValue="") String startDate,
                                  @RequestParam(value = "endDate",required = false,defaultValue="" ) String endDate){
        Map<String, Object> resut = new HashMap<String, Object>();
        List<Object> loan;
        if("".equals(name)){
            resut.put("ret",-1);
            resut.put("msg","name未指定!");
            return resut;
        }
        
        try{
            loan = supplierMapper.queryLoanHistorys(name,startDate,endDate);
            resut.put("loan",loan);
        }catch (Exception e){
            System.out.println(e.getMessage());
            resut.put("ret",-1);
            resut.put("msg",e.getMessage());
        }



        resut.put("ret",0);
        resut.put("msg","请求已处理");
        return resut;
    }
}
