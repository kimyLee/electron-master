/*
 * 
 */
package edu.scut.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.scut.dao.CommonMapper;

/**
 * @Author shuai 1214832893@qq.com
 * @CreateTime 2016年12月11日 上午9:57:06
 * @Description 
 */
@Service("commonService")
public class CommonService {
	@Autowired
	private CommonMapper commonMapper;
	
	@Transactional
	public void load(List<String> tableNames) throws Exception {
		Map<String, Object> nameMap = new HashMap<String,Object>();
		nameMap.put("name", tableNames);
		commonMapper.loadTables();
	}
	
	public List<String> getTables() throws Exception {
		return commonMapper.getTableNames();
	}
	
}
