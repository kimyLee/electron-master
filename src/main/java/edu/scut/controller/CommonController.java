/*
 * 
 */
package edu.scut.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.scut.service.CommonService;

/**
 * @Author shuai 1214832893@qq.com
 * @CreateTime 2016年12月11日 上午9:55:47
 * @Description 
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/load", method = RequestMethod.PUT)
    @ResponseBody
	public Object loadToCsv() throws Exception {
		Map<String, Object> resut = new HashMap<String, Object>();
		List<String> tableNames = commonService.getTables();
//		System.out.println(tableNames);
//		for(String name:tableNames){
//			System.out.println(name);
//		}
		try {
			commonService.load(tableNames);
			resut.put("ret",0);
		    resut.put("msg", "导出成功");
		} catch (Exception e) {
			// TODO: handle exception
			resut.put("ret",-1);
		    resut.put("msg", e.getMessage());
		}
	    return resut;

	}
	
}
