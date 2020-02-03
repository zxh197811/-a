package com.fh.service;

import com.fh.model.User;
import com.fh.model.UserQuery;
import com.fh.util.DataTableResult;

import java.util.List;

public interface UserService {
    DataTableResult selectuser(UserQuery userQuery);

    void addUser(User user);

    User selectUserById(Integer id);

    void updateUser(User user);

    void deleteUser(Integer id);

    void deleteAllUser(List<Integer> ids);
}
