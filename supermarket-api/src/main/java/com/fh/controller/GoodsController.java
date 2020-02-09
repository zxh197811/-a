package com.fh.controller;

import com.fh.model.Area;
import com.fh.model.Goods;
import com.fh.model.GoodsQuery;
import com.fh.service.GoodsService;
import com.fh.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("selectGoods")
        public ServerResponse pageselect(@RequestBody GoodsQuery goodsQuery){
        try {
            DataTableResult dataTableResult=goodsService.pageselect(goodsQuery);
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("addGoods")
    public ServerResponse addGoods(Goods goods){
        try {
            goodsService.addGoods(goods);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("selectGoodsById")
    public ServerResponse selectGoodsById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id = (Integer) redisTemplate.opsForValue().get(token);
            Goods goods=goodsService.selectGoodsById(id);
            return ServerResponse.success(ResponseEnum.SUCCESS,goods);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("updateGoods")
    public ServerResponse updateGoods(Goods goods){
        try {
            Goods goodss=goodsService.selectGoodsById(goods.getId());
            if(StringUtils.isNotBlank(goodss.getFilename())&&!goodss.getFilename().equals(goods.getFilename())){
                AliOssUtil.deleteimage(goodss.getFilename());
            }
            goodsService.updateGoods(goods);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("deleteGoods")
    public ServerResponse deleteGoods(Integer id){
        try {
            goodsService.deleteGoods(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("deleteAllGoods")
    public ServerResponse deleteAllGoods(@RequestParam("ids[]")List<Integer> ids){
        try {
            goodsService.deleteAllGoods(ids);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("selectGoodsByName")
    public ServerResponse selectGoodsByName(String name){
        try {
           Goods goods= goodsService.selectGoodsByName(name);
           if(goods==null){
               return ServerResponse.success(ResponseEnum.SUCCESS,1);
           }else{
               return ServerResponse.success(ResponseEnum.SUCCESS,2);
           }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("selectGoodsByBarcode")
    public ServerResponse selectGoodsByBarcode(String barcode){
        try {
            Goods goods= goodsService.selectGoodsByBarcode(barcode);
            if(goods==null){
                return ServerResponse.success(ResponseEnum.SUCCESS,1);
            }else{
                return ServerResponse.success(ResponseEnum.SUCCESS,2);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("selectAreaByPid")
    public ServerResponse selectAreaByPid(Integer pid){
        try {
            List<Area> areaList= goodsService.selectAreaByPid(pid);
            if(areaList==null){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            return ServerResponse.success(ResponseEnum.SUCCESS,areaList);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("updateStock")
    public ServerResponse updateStock(Integer id,Integer stock){
        try {
            Goods goods=goodsService.updateStock(id,stock);
            if(goods!=null){
                return ServerResponse.success(ResponseEnum.SUCCESS,goods);
            }
            return ServerResponse.success(ResponseEnum.SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("updateStatus")
    public ServerResponse updateStatus(Integer id){
        try {
            goodsService.updateStatus(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("updateAllStatus")
    public ServerResponse updateAllStatus(){
        try {
            goodsService.updateAllStatus();
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("inittoken1")
    public ServerResponse inittoken1(){
        try {
            Long aLong = IdUtil.createnoId();
            String token=aLong+"";
           redisTemplate.opsForHash().put(token,token,token);
           redisTemplate.expire(token,30,TimeUnit.MINUTES);
            return ServerResponse.success(ResponseEnum.SUCCESS,token);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("inittoken2")
    public ServerResponse inittoken2(Integer movieid){
        try {
            String s = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(s,movieid,10, TimeUnit.MINUTES);
            return  ServerResponse.success(ResponseEnum.SUCCESS,s);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }





    @RequestMapping("uploadGoodsImage")
    public ServerResponse uploadGoodsImage(MultipartFile image){
        try {
            String images = AliOssUtil.uploadimage(image.getInputStream(), image.getOriginalFilename(), "images");
            return  ServerResponse.success(ResponseEnum.SUCCESS,images);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


}
