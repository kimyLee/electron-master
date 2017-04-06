/*
 * 
 */
package edu.scut.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @Author shuai 1214832893@qq.com
 * @CreateTime 2016年12月11日 上午9:59:32
 * @Description 
 */
@Repository
public interface CommonMapper {
	void loadTables() throws Exception;

	/**
	 * 
	 */
	List<String> getTableNames() throws Exception;
}
