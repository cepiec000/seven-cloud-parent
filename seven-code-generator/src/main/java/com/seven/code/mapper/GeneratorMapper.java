package com.seven.code.mapper;

import com.seven.code.entity.DatabaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/24 10:51
 */
@Mapper
public interface GeneratorMapper {
    List<DatabaseEntity> queryDatabase();

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Map<String, String>> queryColumns(@Param("databaseName") String databaseName,@Param("tableName") String tableName);
}
