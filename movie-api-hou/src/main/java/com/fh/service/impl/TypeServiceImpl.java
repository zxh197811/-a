package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.MovieTypeMapper;
import com.fh.mapper.TypeMapper;
import com.fh.model.MovieType;
import com.fh.model.Type;
import com.fh.model.TypeQuery;
import com.fh.service.TypeService;
import com.fh.util.DataTableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired(required = false)
    private TypeMapper typeMapper;
    @Autowired(required = false)
    private MovieTypeMapper movieTypeMapper;

    @Override
    public DataTableResult selectTypeBy(TypeQuery typeQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(typeQuery.getName()),"name",typeQuery.getName())
                .ge(typeQuery.getMincreateDate()!=null,"createDate",typeQuery.getMincreateDate())
                .le(typeQuery.getMaxcreateDate()!=null,"createDate",typeQuery.getMaxcreateDate())
                .ge(typeQuery.getMinupdateDate()!=null,"updateDate",typeQuery.getMinupdateDate())
                .le(typeQuery.getMaxupdateDate()!=null,"updateDate",typeQuery.getMaxupdateDate())
                .eq("isdel",0);
        Page page=new Page((typeQuery.getStart()/typeQuery.getLength())+1,typeQuery.getLength());
        IPage iPage = typeMapper.selectPage(page, queryWrapper);
        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setDraw(typeQuery.getDraw());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setData(iPage.getRecords());

        return dataTableResult;
    }


    @Override
    public List<Type> selectType() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("isdel",0);
        return typeMapper.selectList(queryWrapper);
    }


    @Override
    public void addType(Type type) {
        type.setCreateDate(new Date());
        type.setIsdel(0);
        typeMapper.insert(type);
    }


    @Override
    public Type selectTypeById(Integer id) {
        return typeMapper.selectById(id);
    }

    @Override
    public void updateType(Type type) {
        type.setUpdateDate(new Date());
        typeMapper.updateById(type);
    }


    @Override
    public void deleteType(Integer id) {
        Type type = new Type();
        type.setId(id);
        type.setIsdel(1);
        typeMapper.updateById(type);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("typeid", id);
        movieTypeMapper.delete(queryWrapper);
    }


    @Override
    public void deleteAllType(List<Integer> ids) {
        for (Integer id : ids) {
            Type type = new Type();
            type.setId(id);
            type.setIsdel(1);
            typeMapper.updateById(type);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("typeid", ids);
        movieTypeMapper.delete(queryWrapper);
    }
}
