package com.seven.code.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.code.entity.DatabaseEntity;
import com.seven.code.service.GeneratorService;
import com.seven.code.utils.Page;
import com.seven.code.utils.Query;
import com.seven.code.utils.Result;
import com.seven.comm.core.response.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/24 10:49
 */
@RestController
@RequestMapping("/sys/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> list = generatorService.queryList(query);
        PageInfo info = new PageInfo<>(list);
        Page page=new Page(list,info.getTotal(),info.getPageSize(),info.getPageNum());
        return Result.ok().put("page",page);
    }

    @GetMapping("/database/list")
    public Result databaseList(){
        List<DatabaseEntity> databaseEntities = generatorService.queryDatabase();
        return Result.ok().put("list",databaseEntities);
    }



    /**
     * 生成代码
     */
    @GetMapping("/code")
    public void code(String databaseName, String tables,
                     HttpServletResponse response) throws IOException {
        byte[] data = generatorService.generatorCode(databaseName,tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
