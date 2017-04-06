package edu.scut.controller;

import edu.scut.dao.CostMapper;
import edu.scut.dao.SalesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/costs")
public class CostController {

    @Autowired
    private CostMapper costMapper;

    @RequestMapping(value = "/cost", method = RequestMethod.POST)
    @ResponseBody
    public Object addCost(@RequestBody Map<String, Object> model) throws Exception {
        costMapper.insertCost(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/cost", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateCost(@RequestBody Map<String, Object> model) throws Exception {
        costMapper.updateCost(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/cost", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteCost(@RequestBody Map<String, Object> model) throws Exception {
        costMapper.deleteCost(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object searchCost(@RequestParam String key) throws Exception {
        List<Object> costs = costMapper.searchCost(key);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("costs", costs);
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCost(@RequestParam int pageNum, @RequestParam int pageSize) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startRow", (pageNum-1)*pageSize);
        param.put("size", pageSize);
        int total = costMapper.selectCostCount();
        List<Object> costs = costMapper.queryCost(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("total", total);
        result.put("Costs", costs);
        return result;
    }

    @RequestMapping(value = "/costOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object costOrder(@RequestBody Map<String, Object> model) throws Exception {
        costMapper.insertOrder(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/queryCostOrder", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCostOrder(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String delete) throws Exception {
        List<Object> orders = costMapper.queryCostOrder(startDate, endDate, delete);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        resut.put("costOrders", orders);
        return resut;
    }

    @RequestMapping(value = "/deleteCostOrder", method = RequestMethod.PUT)
    @ResponseBody
    public Object deleteCostOrder(@RequestBody Map<String, Object> model) throws Exception {
        costMapper.invalidCostOrder(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }
}
