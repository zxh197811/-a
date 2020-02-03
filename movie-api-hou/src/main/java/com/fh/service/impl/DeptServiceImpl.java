package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.DeptMapper;
import com.fh.model.Dept;
import com.fh.model.DeptQuery;
import com.fh.service.DeptService;
import com.fh.util.DataTableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired(required = false)
    private DeptMapper deptMapper;


    @Override
    public DataTableResult deptselect(DeptQuery deptQuery) {
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(deptQuery.getName()),"name",deptQuery.getName())
                .ge(deptQuery.getMincreateDate()!=null,"createDate",deptQuery.getMincreateDate())
                .le(deptQuery.getMaxcreateDate()!=null,"createDate",deptQuery.getMaxcreateDate());
        Page page=new Page((deptQuery.getStart()/deptQuery.getLength())+1,deptQuery.getLength());
        IPage iPage = deptMapper.selectPage(page, queryWrapper);
        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setData(iPage.getRecords());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setDraw(deptQuery.getDraw());
        return dataTableResult;
    }

    @Override
    public void addDept(Dept dept) {
        dept.setCreateDate(new Date());
        deptMapper.insert(dept);
    }

    @Override
    public Dept selectDeptById(Integer id) {
      return  deptMapper.selectById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdateDate(new Date());
        deptMapper.updateById(dept);
    }


    @Override
    public void deleteDept(Integer id) {
        deptMapper.deleteById(id);
    }


    @Override
    public void deleteAllDept(List<Integer> ids) {
        deptMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Dept> selectdept() {
        return deptMapper.selectList(null);
    }
}
