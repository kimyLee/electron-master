package edu.scut.controller;

import edu.scut.dao.SalesMapper;
import edu.scut.dao.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockMapper stockMapper;


    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    @ResponseBody
    public Object stock(@RequestBody Map<String, Object> model) throws Exception {
        int id = stockMapper.insert(model);
        int type = (Integer) model.get("type");
        List<Map<String, Object>> goods = (List<Map<String, Object>>)model.get("goods");
        for(Map<String, Object> good : goods) {
            int gId = (Integer) good.get("gId");
            String unit = (String) good.get("unit");
            int count = 0;
            if(unit.equals("件")) {
                count = (Integer) good.get("countUnit");
            } else {
                count = (Integer) good.get("count");
            }

            int inventory = stockMapper.selectInventory(gId);
            inventory = type == 0 ? inventory + count : inventory - count;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", gId);
            param.put("inventory", inventory);
            stockMapper.updateInventory(param);
        }

        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateStock(@RequestBody Map<String, Object> model) throws Exception {
        int newStock = (Integer) model.get("inventory");
        int gId = (Integer) model.get("id");
        int oldStock = stockMapper.selectInventory(gId);
        int id = stockMapper.updateInventory(model);
        model.put("huocha", newStock - oldStock);

        stockMapper.insertHuocha(model);


        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/inoutorder", method = RequestMethod.GET)
    @ResponseBody
    public Object inoutorder(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String type, @RequestParam String supplier) throws Exception {
        
    	List<Object> orders = stockMapper.query(startDate, endDate, type, supplier);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        resut.put("orders", orders);
        return resut;
    }

    @RequestMapping(value = "/orderdetail", method = RequestMethod.GET)
    @ResponseBody
    public Object orderdetail(HttpServletRequest request) throws Exception {
    	int id = Integer.parseInt(request.getParameter("id"));
    	Object isdelete = request.getParameter("delete");
    	int delete;
    	if(isdelete == null){
    		delete = 0;
    	}else {
			delete = Integer.parseInt(isdelete.toString());
		}
        List<Object> goods = stockMapper.queryDetail(id,delete);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        resut.put("goods", goods);
        return resut;
    }


}
