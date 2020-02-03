package com.fh.service;

import com.fh.model.Role;
import com.fh.model.RoleQuery;
import com.fh.util.DataTableResult;

import java.util.List;

public interface RoleService {
    DataTableResult selectRole(RoleQuery roleQuery);

    void addRole(Role role);

    Role selectRoleById(Integer id);

    void updateRole(Role role);

    void deleteRole(Integer id);

    void deleteAllRole(List<Integer> ids);

    void updateStatus(Integer id);

    List<Role> roleselect();
}
