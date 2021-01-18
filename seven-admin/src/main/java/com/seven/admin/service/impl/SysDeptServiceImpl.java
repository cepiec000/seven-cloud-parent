package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.entity.SysDeptEntity;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.query.SysDeptQuery;
import com.seven.admin.bean.vo.TreeSelect;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysDeptMapper;
import com.seven.admin.service.SysRoleService;
import com.seven.admin.service.SysUserService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 部门表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysDeptEntity> selectDeptList(SysDeptQuery dept) {
        SevenQueryWrapper<SysDeptEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("parent_id", dept.getParentId());
        queryWrapper.slike("dept_name", dept.getDeptName());
        queryWrapper.seq("status", dept.getStatus());
        queryWrapper.last(dept.getDataScope());
        queryWrapper.orderByAsc("parent_id", "order_num");
        return sysDeptMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysDeptEntity> buildDeptTree(List<SysDeptEntity> depts) {
        List<SysDeptEntity> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDeptEntity dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDeptEntity> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDeptEntity dept = (SysDeptEntity) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDeptEntity> list, SysDeptEntity t) {
        // 得到子节点列表
        List<SysDeptEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDeptEntity tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDeptEntity> getChildList(List<SysDeptEntity> list, SysDeptEntity t) {
        List<SysDeptEntity> tlist = new ArrayList<SysDeptEntity>();
        Iterator<SysDeptEntity> it = list.iterator();
        while (it.hasNext()) {
            SysDeptEntity n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDeptEntity> list, SysDeptEntity t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDeptEntity> depts) {
        List<SysDeptEntity> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        SysRoleEntity role = sysRoleService.selectRoleById(roleId);
        return sysDeptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    @Override
    public SysDeptEntity selectDeptById(Long deptId) {
        return sysDeptMapper.selectById(deptId);
    }

    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        queryWrapper.eq("del_flag", "0");
        queryWrapper.last("and find_in_set(" + deptId + ", ancestors)");
        return sysDeptMapper.selectCount(queryWrapper);
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", deptId);
        queryWrapper.eq("del_flag", "0");
        queryWrapper.last("limit 1");
        int count = sysDeptMapper.selectCount(queryWrapper);
        return count > 0 ? true : false;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int count = sysUserService.selectCountByDeptId(deptId);
        return count > 0 ? true : false;
    }

    @Override
    public String checkDeptNameUnique(SysDeptEntity dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDeptEntity info = checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private SysDeptEntity checkDeptNameUnique(String deptName,Long parentId){
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.eq("dept_name", deptName);
        queryWrapper.last("limit 1");
        return sysDeptMapper.selectOne(queryWrapper);
    }
    @Override
    public int insertDept(SysDeptEntity dept) {
        SysDeptEntity info = selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new SevenException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return sysDeptMapper.insert(dept);
    }

    @Override
    public int updateDept(SysDeptEntity dept) {
        SysDeptEntity newParentDept = selectDeptById(dept.getParentId());
        SysDeptEntity oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = sysDeptMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }
    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDeptEntity dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        sysDeptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDeptEntity> children = sysDeptMapper.selectChildrenDeptById(deptId);
        for (SysDeptEntity child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            sysDeptMapper.updateDeptChildren(children);
        }
    }
    @Override
    public int deleteDeptById(Long deptId) {
        return sysDeptMapper.deleteById(deptId);
    }
}
