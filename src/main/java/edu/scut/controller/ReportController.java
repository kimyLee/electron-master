package edu.scut.controller;

import edu.scut.dao.CostMapper;
import edu.scut.dao.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportMapper reportMapper;

    @RequestMapping(value = "/suppliers", method = RequestMethod.GET)
    @ResponseBody
    public Object querySuppliers(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        List<Object> suppliers = reportMapper.selectSuppliers(startDate, endDate);
        
        List<Object> detail = reportMapper.reportDetail(startDate, endDate);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("suppliers", suppliers);
        resut.put("detail", detail);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public Object queryGoods(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        List<Object> goods = reportMapper.selectGoods(startDate, endDate);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("goods", goods);
        resut.put("msg", "请求已处理");
        return resut;
    }
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public Object queryReport(@RequestParam String startDate, @RequestParam String endDate) throws Exception {


        BigDecimal ticheng = (BigDecimal) reportMapper.selectTichengByDate(startDate, endDate);
        BigDecimal transfer = (BigDecimal) reportMapper.selectTransferByDate(startDate, endDate);
        BigDecimal stock = (BigDecimal) reportMapper.selectStockByDate(startDate, endDate);
        BigDecimal amount = (BigDecimal) reportMapper.selectAmount(startDate, endDate);
        BigDecimal huankuan = (BigDecimal) reportMapper.selectHuankuan(startDate, endDate);


//
        return null;
    }

    @RequestMapping(value = "/qiankuan", method = RequestMethod.GET)
    @ResponseBody
    public Object qiankuan(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        BigDecimal riqian = (BigDecimal) reportMapper.selectRiqian(startDate, endDate);
        BigDecimal zongqian = (BigDecimal) reportMapper.selectZongqian(startDate, endDate);
        double riqian1 = riqian == null ? 0. : riqian.doubleValue();
        double zongqian1 = zongqian == null ? 0. : zongqian.doubleValue();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("qiankuan", riqian1);
        result.put("zongqian", zongqian1);
        result.put("qianqian", zongqian1 - riqian1);
        return result;
    }
    @RequestMapping(value = "/earn", method = RequestMethod.GET)
    @ResponseBody
    public Object earn(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        BigDecimal cost = (BigDecimal) reportMapper.selectCostByDate(startDate, endDate);
        double cost1 = cost == null ? 0. : cost.doubleValue();

        Map<String, Object> sales = reportMapper.selectSaleByDate(startDate, endDate);
        BigDecimal saleMoney = new BigDecimal(0);
        double saleMoney1 = 0;
        try {
        	saleMoney = (BigDecimal) sales.get("saleMoney");
            saleMoney1 = saleMoney == null ? 0. : saleMoney.doubleValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
//        BigDecimal saleMoney = (BigDecimal) sales.get("saleMoney");
//        double saleMoney1 = saleMoney == null ? 0. : saleMoney.doubleValue();

        BigDecimal buhuo = (BigDecimal) reportMapper.selectBuhuo(startDate, endDate);
        double buhuo1 = buhuo == null ? 0 : buhuo.doubleValue();
        BigDecimal ticheng = (BigDecimal) reportMapper.selectTichengByDate(startDate, endDate);
        double ticheng1 = ticheng == null ? 0 : ticheng.doubleValue();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sale", saleMoney1);
        result.put("cost", cost1);
        result.put("buhuo", buhuo1);
        result.put("ticheng", ticheng1);
        result.put("earn", saleMoney1 - cost1 - buhuo1 - ticheng1);
        return result;
    }

    @RequestMapping(value = "/money", method = RequestMethod.GET)
    @ResponseBody
    public Object money(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        BigDecimal shishou = (BigDecimal) reportMapper.selectShishou(startDate, endDate);
        BigDecimal jiekuan = (BigDecimal) reportMapper.selectjiekuan(startDate, endDate);
        BigDecimal cost = (BigDecimal) reportMapper.selectCostByDate(startDate, endDate);
        BigDecimal sanlun = (BigDecimal) reportMapper.selectSanlunByDate(startDate, endDate);
        BigDecimal chuchang = (BigDecimal) reportMapper.selectChunchangByDate(startDate, endDate);
        BigDecimal buhuo = (BigDecimal) reportMapper.selectBuhuo(startDate, endDate);
        BigDecimal ticheng = (BigDecimal) reportMapper.selectTichengByDate(startDate, endDate);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("shishou", getDouble(shishou));
        result.put("jiekuan", getDouble(jiekuan));
        result.put("cost", getDouble(cost));
        result.put("sanlun", getDouble(sanlun));
        result.put("chuchang", getDouble(chuchang));
        result.put("buhuo", getDouble(buhuo));
        result.put("ticheng", getDouble(ticheng));
        result.put("money", getDouble(shishou) - getDouble(jiekuan)
                - getDouble(cost) - getDouble(sanlun)
                - getDouble(chuchang) - getDouble(buhuo)
                - getDouble(ticheng)
        );

        return result;
    }

    @RequestMapping(value = "/sanlun", method = RequestMethod.GET)
    @ResponseBody
    public Object sanlun() throws Exception {
        List<Object> list = reportMapper.selectSanlun();

        return list;
    }
    @RequestMapping(value = "/cost", method = RequestMethod.GET)
    @ResponseBody
    public Object cost() throws Exception {
        List<Object> list = reportMapper.selectCost();

        return list;
    }

    @RequestMapping(value = "/szearn", method = RequestMethod.GET)
    @ResponseBody
    public Object szearn(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
        BigDecimal shouru = (BigDecimal) reportMapper.selectShouru(startDate, endDate);
        BigDecimal zhichu = (BigDecimal) reportMapper.selectZhichu(startDate, endDate);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("in", getDouble(shouru) * -1);
        result.put("out", getDouble(zhichu));
        result.put("szearn", (getDouble(shouru) + getDouble(zhichu)) * -1);

        return result;
    }

    private double getDouble(BigDecimal val) {
        return val == null ? 0 : val.doubleValue();
    }

}
