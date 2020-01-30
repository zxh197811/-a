package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.AreaMapper;
import com.fh.mapper.MovieAreaMapper;
import com.fh.model.Area;
import com.fh.model.AreaQuery;
import com.fh.model.MovieArea;
import com.fh.service.AreaService;
import com.fh.util.DataTableResult;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired(required = false)
    private AreaMapper areaMapper;
    @Autowired(required = false)
    private MovieAreaMapper movieAreaMapper;

    @Override
    public DataTableResult selectAreaBy(AreaQuery areaQuery) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(areaQuery.getName()), "name", areaQuery.getName())
                .ge(areaQuery.getMincreateDate() != null, "createDate", areaQuery.getMincreateDate())
                .le(areaQuery.getMaxcreateDate() != null, "createDate", areaQuery.getMaxcreateDate())
                .ge(areaQuery.getMinupdateDate() != null, "updateDate", areaQuery.getMinupdateDate())
                .le(areaQuery.getMaxupdateDate() != null, "updateDate", areaQuery.getMaxupdateDate())
                .eq("isdel", 0);
        Page page = new Page((areaQuery.getStart() / areaQuery.getLength()) + 1, areaQuery.getLength());
        IPage iPage = areaMapper.selectPage(page, queryWrapper);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setDraw(areaQuery.getDraw());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setData(iPage.getRecords());

        return dataTableResult;
    }


    @Override
    public List<Area> selectArea() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("isdel",0);
        return areaMapper.selectList(queryWrapper);
    }


    @Override
    public void addArea(Area area) {
        area.setCreateDate(new Date());
        area.setIsdel(0);
        areaMapper.insert(area);
    }


    @Override
    public Area selectAreaById(Integer id) {
        return areaMapper.selectById(id);
    }

    @Override
    public void updateArea(Area area) {
        area.setUpdateDate(new Date());
        areaMapper.updateById(area);
    }


    @Override
    public void deleteArea(Integer id) {
        Area area = new Area();
        area.setId(id);
        area.setIsdel(1);
        areaMapper.updateById(area);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("areaid", id);
        movieAreaMapper.delete(queryWrapper);
    }


    @Override
    public void deleteAllArea(List<Integer> ids) {
        for (Integer id : ids) {
            Area area = new Area();
            area.setId(id);
            area.setIsdel(1);
            areaMapper.updateById(area);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("areaid", ids);
        movieAreaMapper.delete(queryWrapper);
    }



}