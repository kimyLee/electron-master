/*
 * 
 */
package edu.scut.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.scut.dao.CustomersMapper;
import edu.scut.dao.GoodsMapper;
import edu.scut.dao.SalesMapper;
import edu.scut.dao.StockMapper;

/**
 * @Author shuai 1214832893@qq.com
 * @CreateTime 2016年12月11日 上午8:47:03
 * @Description 
 */
@Service("salesService")
public class SalesService {
	 @Autowired
	 private CustomersMapper customersMapper;
	 @Autowired
	 private SalesMapper salesMapper;
	 @Autowired
	 private StockMapper stockMapper;
	 @Autowired
	 private GoodsMapper goodsMapper;
	 
	 @Transactional
	 public int addSale( Map<String, Object> model1) throws Exception{
		salesMapper.insert(model1);
		int maxId = salesMapper.selectMaxId();
        salesMapper.insertStock(model1);
        double shishou = Double.parseDouble(model1.get("shishou").toString());
        long id = (Long) model1.get("saleId");
        if (shishou == 0) {
            int cId = (Integer) model1.get("cId");
            String cName = (String) model1.get("cName");
            String cSpell = (String) model1.get("cSpell");
			Double appearanceFee = Double.parseDouble(model1.get("appearanceFee").toString());
			Double carFee = Double.parseDouble(model1.get("carFee").toString());

			if(appearanceFee > 0){
				Map<String, Object> aFee = new HashMap<String, Object>();
				aFee.put("cId", cId);
				aFee.put("cName", cName);
				aFee.put("cSpell", cSpell);
				aFee.put("type", 0);
				aFee.put("supplier", "公司");
				aFee.put("sId", id);
				aFee.put("money", appearanceFee);
				aFee.put("remark","出场费");
				customersMapper.insertDebitBySale(aFee);

				Map<String, Object> old = customersMapper.selectDebitInfo(aFee);
				if (old == null) {
					customersMapper.insertDebitInfo(aFee);
				} else {
					Double money = Double.parseDouble(aFee.get("money").toString());
					BigDecimal old_money = (BigDecimal) old.get("money");
					double loan = money + old_money.doubleValue();
					old.put("money", loan);
					customersMapper.updateDebitInfo(old);
				}
			}
			if(carFee > 0){
				Map<String, Object> cFee = new HashMap<String, Object>();
				cFee.put("cId", cId);
				cFee.put("cName", cName);
				cFee.put("cSpell", cSpell);
				cFee.put("type", 0);
				cFee.put("supplier", "公司");
				cFee.put("sId", id);
				cFee.put("money", carFee);
				cFee.put("remark","三轮车费");
				customersMapper.insertDebitBySale(cFee);

				Map<String, Object> old = customersMapper.selectDebitInfo(cFee);
				if (old == null) {
					customersMapper.insertDebitInfo(cFee);
				} else {
					Double money = Double.parseDouble(cFee.get("money").toString());
					BigDecimal old_money = (BigDecimal) old.get("money");
					double loan = money + old_money.doubleValue();
					old.put("money", loan);
					customersMapper.updateDebitInfo(old);
				}
			}
            List<Map<String, Object>> storeList = (ArrayList) model1.get("storeList");
            for (Map<String, Object> model : storeList) {
                model.put("cId", cId);
                model.put("cName", cName);
                model.put("cSpell", cSpell);
                model.put("type", 0);
                model.put("supplier", model.get("gSupplier"));
                model.put("sId", id);
                customersMapper.insertDebitBySale(model);
//                int i = 1/0; // 测试回滚
                Map<String, Object> old = customersMapper.selectDebitInfo(model);
                if (old == null) {
                    customersMapper.insertDebitInfo(model);
                } else {
                    Double money = Double.parseDouble(model.get("money").toString());
                    BigDecimal old_money = (BigDecimal) old.get("money");
                    double loan = money + old_money.doubleValue();
                    old.put("money", loan);
                    customersMapper.updateDebitInfo(old);
                }
            }
            customersMapper.updateCustomerLoan(cId);
        }

        List<Map<String, Object>> goods = (List<Map<String, Object>>) model1.get("storeList");
        for (Map<String, Object> good : goods) {
            int gId = (Integer) good.get("gId");
            String unit = (String) good.get("unit");
            int count = 0;
            if (unit.equals("件")) {
                count = (Integer) good.get("countUnit");
            } else {
                count = (Integer) good.get("count");
            }

            int inventory = stockMapper.selectInventory(gId);
            inventory = inventory - count;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", gId);
            param.put("inventory", inventory);
            stockMapper.updateInventory(param);
        }

        computeTicheng(goods, id);
        return maxId;
	 }
	 
	 private void computeTicheng(List<Map<String, Object>> list, long sId) throws Exception {
	        List<Map<String, Object>> tichengs = new ArrayList<Map<String, Object>>();
	        for (Map<String, Object> model : list) {
	            int id = (Integer) model.get("gId");
	            Map<String, Object> good = goodsMapper.selectByID(id);
	            Map<String, Object> temp = new HashMap<String, Object>();
	            temp.put("gId", good.get("id"));
	            temp.put("gName", good.get("name"));
	            temp.put("gSupplier", good.get("supplier"));
	            temp.put("sId", sId);
	            if ((Integer) good.get("clearType") == 0)
	                continue;
	            if ((Integer) good.get("clearType") == 1) {
	                BigDecimal unitFee = (BigDecimal) good.get("unitFee");
	                int count = (Integer) model.get("count");
	                double ticheng = unitFee.doubleValue() * count;
	                temp.put("ticheng", ticheng);
	            } else {
	                double ticheng = 0;
	                BigDecimal price1 = (BigDecimal) good.get("price1");
	                BigDecimal price2 = (BigDecimal) good.get("price2");
	                BigDecimal price3 = (BigDecimal) good.get("price3");
	                BigDecimal price4 = (BigDecimal) good.get("price4");
	                BigDecimal price5 = (BigDecimal) good.get("price5");

	                Integer percentage1 = (Integer) good.get("percentage1");
	                Integer percentage2 = (Integer) good.get("percentage2");
	                Integer percentage3 = (Integer) good.get("percentage3");
	                Integer percentage4 = (Integer) good.get("percentage4");
	                Integer percentage5 = (Integer) good.get("percentage5");

	                double price = Double.parseDouble(model.get("gPrice").toString());
	                double money = Double.parseDouble(model.get("money").toString());


	                if (price5 != null && price5.doubleValue() > 0 && price > price5.doubleValue()) {
	                    ticheng = money * percentage5;
	                } else if (price4 != null && price4.doubleValue() > 0 && price > price4.doubleValue()) {
	                    ticheng = money * percentage4 / 100;
	                } else if (price3 != null && price3.doubleValue() > 0 && price > price3.doubleValue()) {
	                    ticheng = money * percentage3 / 100;
	                } else if (price2 != null && price2.doubleValue() > 0 && price > price2.doubleValue()) {
	                    ticheng = money * percentage2 / 100;
	                } else if (price1 != null && price1.doubleValue() > 0 && price > price1.doubleValue()) {
	                    ticheng = money * percentage1 / 100;
	                } else {
	                    continue;
	                }

	                temp.put("ticheng", ticheng);
	            }
	            tichengs.add(temp);
	        }
	        if (tichengs.size() != 0)
	            stockMapper.insertTichengs(tichengs);
	    }
	
	 public Object getSale(HttpServletRequest request) {
		int delete = 0;
		try {
			delete = Integer.parseInt(request.getParameter("delete"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Object salelist = new Object();
		try {
			salelist = salesMapper.selectByPage(delete);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return salelist;
	    	
	}

//	public void insertCompanyDebit( double fee){
//
//	}

	@Transactional
	public void deleteSale(int id)throws Exception{
		Map<String, Object> sale = (Map<String, Object>) salesMapper.selectById(id);

		double appearanceFee = Double.parseDouble(sale.get("appearanceFee").toString());
		double carFee = Double.parseDouble(sale.get("carFee").toString());
		int customerId = Integer.parseInt(sale.get("cId").toString());
		String supplier = "公司";
		salesMapper.deleteDebitBySale(id);
		Map<String, Object> oldDebit = salesMapper.selectCustomerOwnCompany(customerId,supplier);
		double debitMoney = Double.parseDouble(oldDebit.get("money").toString());
		if(appearanceFee > 0){
			debitMoney -= appearanceFee;
		}

		if(carFee > 0){
			debitMoney -= carFee;
		}

		oldDebit.put("money",debitMoney);
		customersMapper.updateDebitInfo(oldDebit);

		BigDecimal shishou = (BigDecimal) sale.get("shishou");
		if (shishou.doubleValue() == 0) {
			List<Map<String, Object>> storeList = salesMapper.selectDetail(id, 0);
			int cId = (Integer) sale.get("cId");
			for (Map<String, Object> model : storeList) {
				model.put("cId", cId);
				Map<String, Object> old = customersMapper.selectDebitInfo(model);

				BigDecimal money = (BigDecimal) model.get("money");
				BigDecimal old_money = (BigDecimal) old.get("money");
				double loan = old_money.doubleValue() - money.doubleValue();
				old.put("money", loan);
				customersMapper.updateDebitInfo(old);

			}
		}
		List<Map<String, Object>> goods = salesMapper.selectDetail(id, 0);
		for (Map<String, Object> good : goods) {
			int gId = (Integer) good.get("gId");
			String unit = (String) good.get("unit");
			int count = 0;
			if (unit.equals("件")) {
				count = (Integer) good.get("countUnit");
			} else {
				count = (Integer) good.get("count");
			}

			int inventory = stockMapper.selectInventory(gId);
			inventory = inventory + count;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", gId);
			param.put("inventory", inventory);
			stockMapper.updateInventory(param);
		}

		salesMapper.delete(id);
	}
}
