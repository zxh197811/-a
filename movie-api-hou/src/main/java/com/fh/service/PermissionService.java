package com.fh.service;

import com.fh.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> selectPermission();

    void addPermission(Permission permission);

    Permission selectPermissionById(Integer id);

    void updatePermission(Permission permission);

    void deleteAllPermission(List<Integer> ids);
}
