package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.dto.AddDeptDTO;
import com.seven.admin.bean.dto.EditDeptDTO;
import com.seven.admin.bean.dto.ZtreeDTO;
import com.seven.admin.bean.entity.SysDeptEntity;
import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.admin.constant.AdminStatCode;
import com.seven.admin.enums.DeleteEnum;
import com.seven.admin.enums.StatusEnum;
import com.seven.admin.mapper.SysDeptMapper;
import com.seven.admin.service.SysDeptService;
import com.seven.admin.service.SysUserService;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.page.Page;
import com.seven.comm.core.page.PageInfo;
import com.seven.comm.core.utils.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 部门管理
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysUserService userService;

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

    @Override
    public Boolean delete(Integer deptId) {
        if (userService.checkUserByDeptId(deptId) > 0) {
            throw new SevenException(AdminStatCode.DEPT_EXIST_USER.getMsg());
        }
        if (checkOnSubDeptCount(deptId) > 0) {
            throw new SevenException(AdminStatCode.DEPT_EXIST_SUB_DEPT.getMsg());
        }
        if (deptMapper.deleteById(deptId) > 0) {
            return true;
        }
        return false;
    }

    private int checkOnSubDeptCount(Integer deptId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", deptId);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return deptMapper.selectCount(queryWrapper);
    }

    @Override
    public SysDeptEntity checkDeptNameUnique(String deptName) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", deptName);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return deptMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean addDept(AddDeptDTO dept) {
        if (checkDeptNameUnique(dept.getName()) != null) {
            throw new SevenException(AdminStatCode.DEPT_EXIST.getMsg());
        }
        SysDeptEntity parentDept = get(dept.getParentId());
        if (parentDept != null && parentDept.getStatus().equals(StatusEnum.INVALID.getCode())) {
            throw new SevenException(AdminStatCode.PARENT_DEPT_INVALID.getMsg());
        }
        SysDeptEntity deptEntity = new SysDeptEntity();
        BeanUtils.copyProperties(dept, deptEntity);
        deptEntity.setCreateTime(new Date());
        deptEntity.setUpdateTime(new Date());
        deptEntity.setDelFlag(DeleteEnum.NO_DELETE.getCode());
        deptEntity.setStatus(StatusEnum.VALID.getCode());
        deptEntity.setAncestores(parentDept.getAncestores() + "," + parentDept.getDeptId());
        if (deptMapper.insert(deptEntity) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SysDeptEntity get(Integer parentId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", parentId);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return deptMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean updateDept(EditDeptDTO dept) {
        SysDeptEntity temp = checkDeptNameUnique(dept.getName());
        if (temp != null && !temp.getDeptId().equals(dept.getDeptId())) {
            throw new SevenException(AdminStatCode.DEPT_EXIST.getMsg());
        }
        SysDeptEntity parentDept = get(dept.getParentId());
        SysDeptEntity oldDept = get(dept.getDeptId());
        if (oldDept == null) {
            throw new SevenException(AdminStatCode.DEPT_NOT_EXIST.getMsg());
        }
        updateDeptTransactional(parentDept, oldDept);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDeptTransactional(SysDeptEntity parentDept, SysDeptEntity oldDept) {
        if (Objects.nonNull(parentDept) && Objects.nonNull(oldDept)) {
            String newAncestore = parentDept.getAncestores() + "," + parentDept.getDeptId();
            String oldAncestore = oldDept.getAncestores();
            updateDeptChildren(oldDept.getDeptId(), newAncestore, oldAncestore);
            oldDept.setAncestores(newAncestore);
        }
        if (oldDept.getStatus().equals(StatusEnum.VALID.getCode())) {
            //则上级所有部门都是有效
            updateParentDeptStatus(StatusEnum.VALID, oldDept.getAncestores());
        }
        deptMapper.updateById(oldDept);
    }

    private int updateParentDeptStatus(StatusEnum valid, String ancestores) {
        Integer[] ids = Convert.toIntArray(ancestores);
        UpdateWrapper<SysDeptEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("dept_id", ids).set("status", valid.getCode());
        return deptMapper.update(null, updateWrapper);
    }

    /**
     * 修改子部门 关系
     *
     * @param deptId
     * @param newAncestore
     * @param oldAncestore
     */
    private void updateDeptChildren(Integer deptId, String newAncestore, String oldAncestore) {
        List<SysDeptEntity> children = queryChildrenByParentId(deptId);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        children.forEach(item -> {
            item.setAncestores(item.getAncestores().replace(oldAncestore, newAncestore));
            item.setUpdateTime(new Date());
        });
        updateBatchById(children);
    }

    @Override
    public List<SysDeptEntity> queryChildrenByParentId(Integer deptId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", deptId);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return deptMapper.selectList(queryWrapper);
    }

    @Override
    public List<ZtreeDTO> queryDeptTree() {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        List<SysDeptEntity> allDept = deptMapper.selectList(queryWrapper);
        return initZtree(allDept);
    }

    @Override
    public int countValidChildrenDeptById(Integer deptId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        queryWrapper.eq("status", StatusEnum.VALID.getCode());
        return deptMapper.selectCount(queryWrapper);
    }

    /**
     * 构建部门树
     *
     * @param allDept
     * @return
     */
    public List<ZtreeDTO> initZtree(List<SysDeptEntity> allDept) {
        return initZtree(allDept, null);
    }

    /**
     * 构建部门树
     *
     * @param deptList
     * @param roleDepts
     * @return
     */
    public List<ZtreeDTO> initZtree(List<SysDeptEntity> deptList, List<String> roleDepts) {
        List<ZtreeDTO> tree = new ArrayList<>();
        boolean isCheck = CollectionUtils.isEmpty(roleDepts);
        deptList.forEach(dept -> {
            if (dept.getStatus().equals(StatusEnum.VALID.getCode())) {
                ZtreeDTO ztree = new ZtreeDTO();
                ztree.setId(dept.getDeptId());
                ztree.setPId(dept.getParentId());
                ztree.setName(dept.getName());
                ztree.setTitle(dept.getName());
                if (!isCheck) {
                    ztree.setChecked(roleDepts.contains(dept.getDeptId() + dept.getName()));
                }
                tree.add(ztree);
            }
        });
        return tree;
    }
}
