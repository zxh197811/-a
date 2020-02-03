package com.fh.service.impl;

import com.fh.mapper.PermissionMapper;
import com.fh.model.Permission;
import com.fh.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired(required = false)
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> selectPermission() {
        return permissionMapper.selectList(null);
    }

    @Override
    public void addPermission(Permission permission) {
            permission.setCreateDate(new Date());
             permissionMapper.insert(permission);
    }

    @Override
    public Permission selectPermissionById(Integer id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public void updatePermission(Permission permission) {
        permission.setUpdateDate(new Date());
             permissionMapper.updateById(permission);
    }

    @Override
    public void deleteAllPermission(List<Integer> ids) {
          permissionMapper.deleteBatchIds(ids);
    }
}
