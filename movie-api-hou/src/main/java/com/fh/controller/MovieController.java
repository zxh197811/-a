package com.fh.controller;

import com.fh.model.Movie;
import com.fh.model.MovieQuery;
import com.fh.model.Photo;
import com.fh.service.MovieService;
import com.fh.util.AliOssUtil;
import com.fh.util.DataTableResult;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("pageselect")
    public ServerResponse pageselect(@RequestBody MovieQuery movieQuery){
        try {
            DataTableResult dataTableResult= movieService.pageselect(movieQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("addMovie")
    public ServerResponse addMovie(Movie movie){
        try {
            movieService.addMovie(movie);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectMovieById")
    public ServerResponse selectMovieById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer movieid= (Integer) redisTemplate.opsForValue().get(token);
            Movie movie=movieService.selectMovieById(movieid);
            return  ServerResponse.success(ResponseEnum.SUCCESS,movie);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("updateMovie")
    public ServerResponse updateMovie(Movie movie){
        try {
          Movie movie1=movieService.selectMovieById1(movie.getId());
            if(StringUtils.isNotBlank(movie.getMainimage())&&!movie.getMainimage().equals(movie1.getMainimage())){
                AliOssUtil.deleteimage(movie1.getMainimage());
            }
            movieService.updateMovie(movie);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteMovie")
    public ServerResponse deleteMovie(Integer id){
        try {
            movieService.deleteMovie(id);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllMovie")
    public ServerResponse deleteAllMovie(@RequestParam("ids[]") List<Integer> ids){
        try {
            movieService.deleteAllMovie(ids);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("uploadimage")
    public ServerResponse uploadimage(MultipartFile image){
        try {
            String images = AliOssUtil.uploadimage(image.getInputStream(), image.getOriginalFilename(), "images");
            return  ServerResponse.success(ResponseEnum.SUCCESS,images);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("uploadimagemageger")
    public ServerResponse uploadimagemageger(MultipartFile image,String token){
        try {
            String images = AliOssUtil.uploadimage(image.getInputStream(), image.getOriginalFilename(), "images");
            Photo photo=new Photo();
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer movieid= (Integer) redisTemplate.opsForValue().get(token);
            photo.setMovieid(movieid);
            photo.setName(image.getOriginalFilename());
            photo.setUploadDate(new Date());
            photo.setUrl(images);
            movieService.addImage(photo);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }



    @RequestMapping("imagemanage")
    public ServerResponse imagemanage(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer movieid= (Integer) redisTemplate.opsForValue().get(token);
            return  movieService.imagemanage(movieid);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("inittoken")
    public ServerResponse inittoken(Integer movieid){
        try {
            String s = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(s,movieid,1, TimeUnit.MINUTES);
            return  ServerResponse.success(ResponseEnum.SUCCESS,s);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteimage")
    public ServerResponse deleteimage(Integer imageid){
        try {
            Photo photo=movieService.selectImageById(imageid);
            AliOssUtil.deleteimage(photo.getUrl());
            movieService.deleteimage(imageid);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("deleteAllimage")
    public ServerResponse deleteAllimage(@RequestParam("ids[]") List<Integer> ids){
        try {
            for (Integer id : ids) {
                Photo photo=movieService.selectImageById(id);
                AliOssUtil.deleteimage(photo.getUrl());
            }
            movieService.deleteAllimage(ids);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


}
