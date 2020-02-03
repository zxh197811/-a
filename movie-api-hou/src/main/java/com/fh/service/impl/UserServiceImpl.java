package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.DeptMapper;
import com.fh.mapper.UserMapper;
import com.fh.mapper.UserRoleMapper;
import com.fh.model.Dept;
import com.fh.model.User;
import com.fh.model.UserQuery;
import com.fh.model.UserRole;
import com.fh.service.UserService;
import com.fh.util.DataTableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;
    @Autowired(required = false)
    private DeptMapper deptMapper;


    @Override
    public DataTableResult selectuser(UserQuery userQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(userQuery.getName()),"name",userQuery.getName())
                .like(StringUtils.isNotBlank(userQuery.getRealname()),"realname",userQuery.getRealname())
                .like(StringUtils.isNotBlank(userQuery.getPhonenumber()),"phonenumber",userQuery.getPhonenumber())
                .like(StringUtils.isNotBlank(userQuery.getEmail()),"email",userQuery.getEmail())
                .eq(userQuery.getSex()!=null,"sex",userQuery.getSex())
                .ge(userQuery.getMinbirthday()!=null,"birthday",userQuery.getMinbirthday())
                .le(userQuery.getMaxbirthday()!=null,"birthday",userQuery.getMaxbirthday())
                .ge(userQuery.getMincreateDate()!=null,"createDate",userQuery.getMincreateDate())
                .le(userQuery.getMaxcreateDate()!=null,"createDate",userQuery.getMaxcreateDate())
                .ge(userQuery.getMinupdateDate()!=null,"updateDate",userQuery.getMinupdateDate())
                .le(userQuery.getMaxupdateDate()!=null,"updateDate",userQuery.getMaxupdateDate());
        Page page=new Page((userQuery.getStart()/userQuery.getLength())+1,userQuery.getLength());
        IPage iPage = userMapper.selectPage(page, queryWrapper);
        List<User> records = iPage.getRecords();
        for (User record : records) {
            if(record.getDeptid()!=null){
                Dept dept = deptMapper.selectById(record.getDeptid());
                record.setDeptname(dept.getName());
            }
        }
        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setData(records);
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setDraw(userQuery.getDraw());
        return dataTableResult;
    }


    @Override
    public void addUser(User user) {
        user.setCreateDate(new Date());
        userMapper.insert(user);
        if(StringUtils.isNotBlank(user.getRoleids())){
            String[] split = user.getRoleids().split(",");
            for (int i = 0; i < split.length; i++) {
                UserRole userRole=new UserRole();
                userRole.setUserid(user.getId());
                userRole.setRoleid(Integer.parseInt(split[i]));
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public User selectUserById(Integer id) {
        User user = userMapper.selectById(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",id);
        List<UserRole> list = userRoleMapper.selectList(queryWrapper);
        String roleids="";
        for (UserRole userRole : list) {
            roleids+=","+userRole.getRoleid();
        }
        if(roleids.length()>0){
            user.setRoleids(roleids.substring(1));
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
      user.setUpdateDate(new Date());
      userMapper.updateById(user);
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("userid",user.getId());
        userRoleMapper.delete(updateWrapper);
        if(StringUtils.isNotBlank(user.getRoleids())){
            String[] split = user.getRoleids().split(",");
            for (int i = 0; i < split.length; i++) {
                UserRole userRole=new UserRole();
                userRole.setUserid(user.getId());
                userRole.setRoleid(Integer.parseInt(split[i]));
                userRoleMapper.insert(userRole);
            }
        }


    }

    @Override
    public void deleteUser(Integer id) {
          userMapper.deleteById(id);
          UpdateWrapper updateWrapper=new UpdateWrapper();
          updateWrapper.eq("userid",id);
          userRoleMapper.delete(updateWrapper);
    }

    @Override
    public void deleteAllUser(List<Integer> ids) {
          userMapper.deleteBatchIds(ids);
          UpdateWrapper updateWrapper=new UpdateWrapper();
          updateWrapper.in("userid",ids);
          userRoleMapper.delete(updateWrapper);
    }
}
