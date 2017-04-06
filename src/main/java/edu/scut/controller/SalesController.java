package edu.scut.controller;

import edu.scut.dao.CustomersMapper;
import edu.scut.dao.GoodsMapper;
import edu.scut.dao.SalesMapper;
import edu.scut.dao.StockMapper;
import edu.scut.service.SalesService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private CustomersMapper customersMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Resource
    private SalesService salesService;

    @RequestMapping(value = "/sale", method = RequestMethod.POST)
    @ResponseBody
    public Object addSale(@RequestBody Map<String, Object> model1){
    	 Map<String, Object> resut = new HashMap<String, Object>();
    	try {
			int lastId = salesService.addSale(model1);
			resut.put("lastId", lastId);
			resut.put("ret", 0);
	        resut.put("msg", "请求已处理");
		} catch (Exception e) {
			// TODO: handle exception
			resut.put("ret", -1);
	        resut.put("msg", "添加失败");
		}
       
        
        return resut;
    }
    
    @RequestMapping(value = "/sale", method = RequestMethod.GET)
    @ResponseBody
    public Object sale(HttpServletRequest request) throws Exception {
        return salesService.getSale(request);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object querySale(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(value="delete",required=false, defaultValue = "0") String isDelete) throws Exception {
        return salesMapper.selectByDate(startDate, endDate,isDelete);
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    @ResponseBody
//    public Object queryDeleteSale(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
//
//        return salesMapper.selectDeleteByDate(startDate, endDate);
//    }

    @RequestMapping(value = "/sale", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteSale(@RequestBody int id){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            salesService.deleteSale(id);
        }catch (Exception e){
            result.put("ret", -1);
            result.put("msg", "删除未成功");
            return result;
        }

        result.put("ret", 0);
        result.put("msg", "删除成功");
        return result;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object saleDetail(@RequestParam int id,@RequestParam(value = "delete",required = false,defaultValue = "0") int isDelete) throws Exception {
        return salesMapper.selectDetail(id,isDelete);
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public Object saleGoods(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String supplier) throws Exception {
        return salesMapper.selectGoodsByDate(startDate, endDate, supplier);
    }

    @RequestMapping(value = "/good", method = RequestMethod.GET)
    @ResponseBody
    public Object saleGood(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int id) throws Exception {
        return salesMapper.selectGoodsIDByDate(startDate, endDate, id);
    }

}
