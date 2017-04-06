package edu.scut.controller;

import edu.scut.dao.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/6/2.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsMapper goodsMapper;

    @RequestMapping(value="/good", method = RequestMethod.POST)
    @ResponseBody
    public Object addGood(@RequestBody Map<String, Object> model) throws Exception {
        goodsMapper.insert(model);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value="/good", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateGood(@RequestBody Map<String, Object> model) throws Exception {
        goodsMapper.update(model);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value="/good", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteGood(@RequestBody int id) throws Exception {
        goodsMapper.delete(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    @ResponseBody
    public Object searchGood(@RequestParam String key) throws Exception {
        List<Object> goods = goodsMapper.search(key);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("goods", goods);
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object queryGoods(@RequestParam int pageNum, @RequestParam int pageSize) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startRow", (pageNum-1)*pageSize);
        param.put("size", pageSize);
        List<Object> customers = goodsMapper.query(param);
        int total = goodsMapper.selectCount();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ret", 0);
        result.put("msg", "");
        result.put("total", total);
        result.put("customers", customers);
        return result;
    }

}
