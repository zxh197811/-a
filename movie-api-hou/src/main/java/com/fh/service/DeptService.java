package com.fh.service;

import com.fh.model.Dept;
import com.fh.model.DeptQuery;
import com.fh.util.DataTableResult;

import java.util.List;

public interface DeptService {
    DataTableResult deptselect(DeptQuery deptQuery);

    void addDept(Dept dept);

    Dept selectDeptById(Integer id);

    void updateDept(Dept dept);

    void deleteDept(Integer id);

    void deleteAllDept(List<Integer> ids);

    List<Dept> selectdept();
}
