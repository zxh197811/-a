package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.AreaMapper;
import com.fh.mapper.GoodsMapper;
import com.fh.model.Area;
import com.fh.model.Goods;
import com.fh.model.GoodsQuery;
import com.fh.service.GoodsService;
import com.fh.util.DataTableResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired(required = false)
    private GoodsMapper goodsMapper;
    @Autowired(required = false)
    private AreaMapper areaMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public DataTableResult pageselect(GoodsQuery goodsQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(goodsQuery.getName()),"name",goodsQuery.getName())
                .ge(goodsQuery.getMinprice()!=null,"price",goodsQuery.getMinprice())
                .le(goodsQuery.getMaxprice()!=null,"price",goodsQuery.getMaxprice())
                .ge(goodsQuery.getMinsales()!=null,"sales",goodsQuery.getMinsales())
                .le(goodsQuery.getMaxsales()!=null,"sales",goodsQuery.getMaxsales())
                .like(StringUtils.isNotBlank(goodsQuery.getBarcode()),"barcode",goodsQuery.getBarcode())
                .ge(goodsQuery.getMinstock()!=null,"stock",goodsQuery.getMinstock())
                .le(goodsQuery.getMaxstock()!=null,"stock",goodsQuery.getMaxstock())
                .ge(goodsQuery.getMinproducedDate()!=null,"producedDate",goodsQuery.getMinproducedDate())
                .le(goodsQuery.getMaxproducedDate()!=null,"producedDate",goodsQuery.getMaxproducedDate())
                .ge(goodsQuery.getMinshelflife()!=null,"shelflife",goodsQuery.getMinshelflife())
                .le(goodsQuery.getMaxshelflife()!=null,"shelflife",goodsQuery.getMaxshelflife())
                .eq(goodsQuery.getStatus()!=null,"status",goodsQuery.getStatus())
                .eq(goodsQuery.getAreaid()!=null,"areaid",goodsQuery.getAreaid())
                 .eq("isdel",0);
        Page page=new Page((goodsQuery.getStart()/goodsQuery.getLength())+1,goodsQuery.getLength());
        IPage iPage = goodsMapper.selectPage(page, queryWrapper);
        DataTableResult dataTableResult=new DataTableResult();
        List<Goods> records = iPage.getRecords();
        for (Goods record : records) {
            Area area = areaMapper.selectById(record.getAreaid());
            if(area!=null){
                Area area1 = areaMapper.selectById(area.getPid());
                if(area1!=null){
                    record.setAreaname(area1.getName()+"/"+area.getName());
                }
            }
        }
        dataTableResult.setDraw(goodsQuery.getDraw());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setData(records);

        return dataTableResult;
    }

    @Override
    public void addGoods(Goods goods) {
        goods.setIsdel(0);
        goodsMapper.insert(goods);
    }

    @Override
    public Goods selectGoodsById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        Area area = areaMapper.selectById(goods.getAreaid());
        if(area!=null){
            Area area1 = areaMapper.selectById(area.getPid());
            goods.setAreaid2(area1.getId());
        }
        return goods;
    }


    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
    }

    @Override
    public void deleteGoods(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if(goods!=null){
            goods.setIsdel(1);
            goodsMapper.updateById(goods);
        }
    }

    @Override
    public void deleteAllGoods(List<Integer> ids) {
        List<Goods> goods = goodsMapper.selectBatchIds(ids);
        for (Goods good : goods) {
            if(good!=null){
                good.setIsdel(1);
                goodsMapper.updateById(good);
            }
        }
    }


    @Override
    public Goods selectGoodsByName(String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(name)) {
            queryWrapper.eq("name",name);
            return goodsMapper.selectOne(queryWrapper);
        }
        return new Goods();
    }

    @Override
    public Goods selectGoodsByBarcode(String barcode) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(barcode)) {
            queryWrapper.eq("barcode",barcode);
            return goodsMapper.selectOne(queryWrapper);
        }
        return new Goods();
    }

    @Override
    public List<Area> selectAreaByPid(Integer pid) {
        if(pid!=null){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("pid",pid);
            List<Area> list = areaMapper.selectList(queryWrapper);
            return list;
        }

        return null;
    }


    @Override
    public Goods updateStock(Integer id, Integer stock) {
        Goods goods = goodsMapper.selectById(id);
        if(stock>goods.getStock()){
            return goods;
        }else{
           Integer count= goodsMapper.updateStock(stock,id);
           if(count>0){
               return null;
           }else{
              return goods;
            }
        }
    }

    @Override
    public void updateStatus(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if(goods!=null){
            goods.setStatus(goods.getStatus()==0?1:0);
            goodsMapper.updateById(goods);
        }
    }


    @Override
    public void updateAllStatus() {
        List<Goods> goods = goodsMapper.selectList(null);
        for (Goods good : goods) {
            Date date = DateUtils.addMonths(good.getProducedDate(), good.getShelflife());
            if(date.compareTo(new Date())<0&&good.getStatus()==0){
                good.setStatus(1);
                goodsMapper.updateById(good);
            }
        }

    }
}
