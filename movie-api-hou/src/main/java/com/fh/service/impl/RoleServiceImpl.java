package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.RoleMapper;
import com.fh.mapper.RolePermissionMapper;
import com.fh.model.Role;
import com.fh.model.RolePermission;
import com.fh.model.RoleQuery;
import com.fh.service.RoleService;
import com.fh.util.DataTableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired(required = false)
    private RoleMapper roleMapper;
    @Autowired(required = false)
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public DataTableResult selectRole(RoleQuery roleQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(roleQuery.getName()),"name",roleQuery.getName())
                .eq(roleQuery.getStatus()!=null,"status",roleQuery.getStatus())
                .ge(roleQuery.getMincreateDate()!=null,"createDate",roleQuery.getMincreateDate())
                .le(roleQuery.getMaxcreateDate()!=null,"createDate",roleQuery.getMaxcreateDate());
        Page p=new Page((roleQuery.getStart()/roleQuery.getLength())+1,roleQuery.getLength());
        IPage iPage = roleMapper.selectPage(p, queryWrapper);
        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setDraw(roleQuery.getDraw());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setData(iPage.getRecords());
        return dataTableResult;
    }

    @Override
    public void addRole(Role role) {
            role.setCreateDate(new Date());
            roleMapper.insert(role);
            if(StringUtils.isNotBlank(role.getPermissionid())){
                String[] split = role.getPermissionid().split(",");
                for (int i = 0; i < split.length; i++) {
                    RolePermission rolePermission=new RolePermission();
                    rolePermission.setPermissionid(Integer.parseInt(split[i]));
                    rolePermission.setRoleid(role.getId());
                    rolePermissionMapper.insert(rolePermission);
                }
            }
    }

    @Override
    public Role selectRoleById(Integer id) {
        Role role = roleMapper.selectById(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("roleid",id);
        List<RolePermission> list = rolePermissionMapper.selectList(queryWrapper);
        String permissionids="";
        for (RolePermission rolePermission : list) {
            permissionids+=","+rolePermission.getPermissionid();
        }
        if(permissionids.length()>0){
            role.setPermissionid(permissionids.substring(1));
        }
        return role;
    }

    @Override
    public void updateRole(Role role) {
        role.setUpdateDate(new Date());
        roleMapper.updateById(role);
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("roleid",role.getId());
        rolePermissionMapper.delete(updateWrapper);
        if(StringUtils.isNotBlank(role.getPermissionid())){
            String[] split = role.getPermissionid().split(",");
            for (int i = 0; i < split.length; i++) {
                RolePermission rolePermission=new RolePermission();
                rolePermission.setPermissionid(Integer.parseInt(split[i]));
                rolePermission.setRoleid(role.getId());
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    @Override
    public void deleteRole(Integer id) {
        roleMapper.deleteById(id);
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("roleid",id);
        rolePermissionMapper.delete(updateWrapper);
    }

    @Override
    public void deleteAllRole(List<Integer> ids) {
         roleMapper.deleteBatchIds(ids);
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.in("roleid",ids);
        rolePermissionMapper.delete(updateWrapper);
    }

    @Override
    public void updateStatus(Integer id) {
        Role role = roleMapper.selectById(id);
        role.setStatus(role.getStatus()==0?1:0);
        roleMapper.updateById(role);
    }

    @Override
    public List<Role> roleselect() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",0);
        return roleMapper.selectList(queryWrapper);
    }
}
