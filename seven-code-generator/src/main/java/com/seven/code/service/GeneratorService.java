package com.seven.code.service;

import com.seven.code.entity.DatabaseEntity;
import com.seven.code.mapper.GeneratorMapper;
import com.seven.code.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author v_chendongdong
 * @date 2020/12/24
 */
@Service
public class GeneratorService {
	@Autowired
	private GeneratorMapper generatorMapper;

	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return generatorMapper.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return generatorMapper.queryTotal(map);
	}

	public List<DatabaseEntity> queryDatabase(){
		return generatorMapper.queryDatabase();
	}

	public Map<String, String> queryTable(String databaseName,String tableName) {
		return generatorMapper.queryTable(databaseName,tableName);
	}

	public List<Map<String, String>> queryColumns(String databaseName,String tableName) {
		return generatorMapper.queryColumns(databaseName,tableName);
	}

	public byte[] generatorCode(String databaseName,String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(databaseName,tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(databaseName,tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
