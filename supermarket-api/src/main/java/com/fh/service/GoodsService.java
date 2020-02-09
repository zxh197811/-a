package com.fh.service;

import com.fh.model.Area;
import com.fh.model.Goods;
import com.fh.model.GoodsQuery;
import com.fh.util.DataTableResult;

import java.util.List;

public interface GoodsService {
    DataTableResult pageselect(GoodsQuery goodsQuery);

    void addGoods(Goods goods);

    Goods selectGoodsById(Integer id);

    void updateGoods(Goods goods);

    void deleteGoods(Integer id);

    void deleteAllGoods(List<Integer> ids);

    Goods selectGoodsByName(String name);

    Goods selectGoodsByBarcode(String barcode);

    List<Area> selectAreaByPid(Integer pid);

    Goods updateStock(Integer id, Integer stock);

    void updateStatus(Integer id);

    void updateAllStatus();
}
