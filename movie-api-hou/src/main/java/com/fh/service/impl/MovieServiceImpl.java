package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.*;
import com.fh.model.*;
import com.fh.service.MovieService;
import com.fh.util.DataTableResult;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired(required = false)
    private MovieMapper movieMapper;
    @Autowired(required = false)
    private MovieAreaMapper movieAreaMapper;
    @Autowired(required = false)
    private MovieTypeMapper movieTypeMapper;
    @Autowired(required = false)
    private AreaMapper areaMapper;
    @Autowired(required = false)
    private TypeMapper typeMapper;
    @Autowired(required = false)
    private PhotoMapper photoMapper;
    @Override
    public DataTableResult pageselect(MovieQuery movieQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(movieQuery.getName()),"name",movieQuery.getName())
                .ge(movieQuery.getMingrade()!=null,"grade",movieQuery.getMingrade())
                .le(movieQuery.getMaxgrade()!=null,"grade",movieQuery.getMaxgrade())
                .eq(movieQuery.getIshot()!=null,"ishot",movieQuery.getIshot())
                .le(movieQuery.getIsshow()!=null&&movieQuery.getIsshow()==0,"showtime",new Date())
                .ge(movieQuery.getIsshow()!=null&&movieQuery.getIsshow()==1,"showtime",new Date())
                .ge(movieQuery.getDecade()!=null&&movieQuery.getDecade()==1,"showtime",new Date(80,0,1))
                .lt(movieQuery.getDecade()!=null&&movieQuery.getDecade()==1,"showtime",new Date(90,0,1))
                .ge(movieQuery.getDecade()!=null&&movieQuery.getDecade()==2,"showtime",new Date(90,0,1))
                .lt(movieQuery.getDecade()!=null&&movieQuery.getDecade()==2,"showtime",new Date(100,0,1))
                .ge(movieQuery.getDecade()!=null&&movieQuery.getDecade()==3,"showtime",new Date(100,0,1))
                .lt(movieQuery.getDecade()!=null&&movieQuery.getDecade()==3,"showtime",new Date(110,0,1))
                .ge(movieQuery.getDecade()!=null&&movieQuery.getDecade()==4,"showtime",new Date(110,0,1))
                .le(movieQuery.getDecade()!=null&&movieQuery.getDecade()==4,"showtime",new Date(120,0,1))
                .ge(movieQuery.getMinshowDate()!=null,"showtime",movieQuery.getMinshowDate())
                .le(movieQuery.getMaxshowDate()!=null,"showtime",movieQuery.getMaxshowDate())
                .eq("isdel",0);
        if(movieQuery.getAreaid()!=null){
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("areaid",movieQuery.getAreaid());
            List<MovieArea> list = movieAreaMapper.selectList(queryWrapper1);
            if(!list.isEmpty()){
                List<Integer> ids=new ArrayList<>();
                for (MovieArea movieArea : list) {
                    ids.add(movieArea.getMovieid());
                }
                queryWrapper.in("id",ids);
            }else{
                DataTableResult dataTableResult=new DataTableResult();
                dataTableResult.setDraw(movieQuery.getDraw());
                dataTableResult.setRecordsTotal(0);
                dataTableResult.setRecordsFiltered(0);
                dataTableResult.setData(new ArrayList());
                return dataTableResult;
            }
        }
       if(movieQuery.getTypeid()!=null){
           QueryWrapper queryWrapper2=new QueryWrapper();
           queryWrapper2.eq("typeid",movieQuery.getTypeid());
           List<MovieType> list2 = movieTypeMapper.selectList(queryWrapper2);
           if(!list2.isEmpty()){
               List<Integer> ids=new ArrayList<>();
               for (MovieType movieType : list2) {
                   ids.add(movieType.getMovieid());
               }
               queryWrapper.in("id",ids);
           }else{
               DataTableResult dataTableResult=new DataTableResult();
               dataTableResult.setDraw(movieQuery.getDraw());
               dataTableResult.setRecordsTotal(0);
               dataTableResult.setRecordsFiltered(0);
               dataTableResult.setData(new ArrayList());
               return dataTableResult;
           }
       }
        Page page=new Page((movieQuery.getStart()/movieQuery.getLength())+1,movieQuery.getLength());
        IPage iPage = movieMapper.selectPage(page, queryWrapper);
        List<Movie> records = iPage.getRecords();
        if(!records.isEmpty()){
            for (Movie record : records) {
               QueryWrapper queryWrapper3=new QueryWrapper();
               queryWrapper3.eq("movieid",record.getId());
                List<MovieArea> list3  = movieAreaMapper.selectList(queryWrapper3);
                if(!list3.isEmpty()){
                    List<Integer> areaids=new ArrayList<>();
                    for (MovieArea movieArea : list3) {
                       areaids.add(movieArea.getAreaid());
                    }
                    QueryWrapper queryWrapper4=new QueryWrapper();
                    queryWrapper4.in("id",areaids);
                    List<Area> areaList = areaMapper.selectList(queryWrapper4);
                    if(!areaList.isEmpty()){
                        String areaname="";
                        for (Area area : areaList) {
                            areaname+=","+area.getName();
                        }
                        record.setAreaname(areaname.substring(1));
                    }
                }

                List<MovieType> list4  = movieTypeMapper.selectList(queryWrapper3);

                if(!list4.isEmpty()){
                    List<Integer> typeids=new ArrayList<>();
                    for (MovieType movieType : list4) {
                        typeids.add(movieType.getTypeid());
                    }
                    QueryWrapper queryWrapper4=new QueryWrapper();
                    queryWrapper4.in("id",typeids);
                    List<Type> typeList = typeMapper.selectList(queryWrapper4);
                    if(!typeList.isEmpty()){
                        String typename="";
                        for (Type type : typeList) {
                            typename+=","+type.getName();
                        }
                        record.setTypename(typename.substring(1));
                    }
                }
            }
        }
       
        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setDraw(movieQuery.getDraw());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setData(records);

        return dataTableResult;
    }


    @Override
    public void addMovie(Movie movie) {
        movie.setCreateDate(new Date());
        movie.setIsdel(0);
        movieMapper.insert(movie);
        String areaids = movie.getAreaids();
        if(StringUtils.isNotBlank(areaids)){
            String[] split = areaids.split(",");
            for (String s : split) {
                MovieArea movieArea=new MovieArea();
                movieArea.setAreaid(Integer.parseInt(s));
                movieArea.setMovieid(movie.getId());
                movieAreaMapper.insert(movieArea);
            }
        }
        String typeids = movie.getTypeids();
        if(StringUtils.isNotBlank(typeids)){
            String[] split = typeids.split(",");
            for (String s : split) {
                MovieType movieType=new MovieType();
                movieType.setTypeid(Integer.parseInt(s));
                movieType.setMovieid(movie.getId());
                movieTypeMapper.insert(movieType);
            }
        }
    }


    @Override
    public Movie selectMovieById(Integer id) {
        Movie movie = movieMapper.selectById(id);
        QueryWrapper queryWrapper3=new QueryWrapper();
        queryWrapper3.eq("movieid",id);
        List<MovieArea> list3  = movieAreaMapper.selectList(queryWrapper3);
        if(!list3.isEmpty()){
           String areaids="";
            for (MovieArea movieArea : list3) {
                areaids+=","+movieArea.getAreaid();
            }
                movie.setAreaids(areaids.substring(1));
        }

        List<MovieType> list4  = movieTypeMapper.selectList(queryWrapper3);
        if(!list4.isEmpty()){
            String typeids="";
            for (MovieType movieType : list4) {
                typeids+=","+movieType.getTypeid();
            }
                movie.setTypeids(typeids.substring(1));
        }


        return movie;
    }

    @Override
    public Movie selectMovieById1(Integer id) {
        return movieMapper.selectById(id);
    }

    @Override
    public void updateMovie(Movie movie) {
        movie.setUpdateDate(new Date());
        movieMapper.updateById(movie);
        QueryWrapper queryWrapper3=new QueryWrapper();
        queryWrapper3.eq("movieid",movie.getId());
        movieAreaMapper.delete(queryWrapper3);
        movieTypeMapper.delete(queryWrapper3);
        String areaids = movie.getAreaids();
        if(StringUtils.isNotBlank(areaids)){
            String[] split = areaids.split(",");
            for (String s : split) {
                MovieArea movieArea=new MovieArea();
                movieArea.setAreaid(Integer.parseInt(s));
                movieArea.setMovieid(movie.getId());
                movieAreaMapper.insert(movieArea);
            }
        }
        String typeids = movie.getTypeids();
        if(StringUtils.isNotBlank(typeids)){
            String[] split = typeids.split(",");
            for (String s : split) {
                MovieType movieType=new MovieType();
                movieType.setTypeid(Integer.parseInt(s));
                movieType.setMovieid(movie.getId());
                movieTypeMapper.insert(movieType);
            }
        }

    }


    @Override
    public void deleteMovie(Integer id) {
        Movie movie=new Movie();
        movie.setId(id);
        movie.setIsdel(1);
        movieMapper.updateById(movie);
    }

    @Override
    public void deleteAllMovie(List<Integer> ids) {
        for (Integer id : ids) {
            Movie movie=new Movie();
            movie.setId(id);
            movie.setIsdel(1);
            movieMapper.updateById(movie);
        }
    }

    @Override
    public ServerResponse imagemanage(Integer movieid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("movieid",movieid);
        List<Photo> list = photoMapper.selectList(queryWrapper);
        return ServerResponse.success(ResponseEnum.SUCCESS,list);
    }


    @Override
    public void addImage(Photo photo) {
        photoMapper.insert(photo);
    }


    @Override
    public void deleteimage(Integer imageid) {
        photoMapper.deleteById(imageid);
    }


    @Override
    public void deleteAllimage(List<Integer> ids) {
        photoMapper.deleteBatchIds(ids);
    }

    @Override
    public Photo selectImageById(Integer imageid) {
        return photoMapper.selectById(imageid);
    }
}
