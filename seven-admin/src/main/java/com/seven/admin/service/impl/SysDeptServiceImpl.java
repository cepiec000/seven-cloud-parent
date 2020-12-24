package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.entity.SysDeptEntity;
import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.admin.mapper.SysDeptMapper;
import com.seven.admin.service.SysDeptService;
import com.seven.comm.core.page.Page;
import com.seven.comm.core.page.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 部门管理
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public PageInfo<DeptVO> queryByPage(DeptQuery deptQuery) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        PageInfo<DeptVO> pageInfo = new PageInfo<>();
        Page<SysDeptEntity> page = deptMapper.selectByPage(queryWrapper, deptQuery.getPageNo(), deptQuery.getSize());

        List<SysDeptEntity> list = page.getRows();
        if (CollectionUtils.isEmpty(list) || page.getTotal() == 0) {
            return pageInfo;
        }
        List<DeptVO> nList = new ArrayList<>(list.size());
        list.forEach(dept -> {
            DeptVO deptVO = new DeptVO();
            BeanUtils.copyProperties(dept, deptVO);
            nList.add(deptVO);
        });
        pageInfo.setRows(nList);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setIndex(deptQuery.getPageNo());
        pageInfo.setSize(deptQuery.getSize());
        return pageInfo;
    }
}
