package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.mapper.AreaMapper;
import com.fh.mapper.FlightMapper;
import com.fh.mapper.TicketMapper;
import com.fh.mapper.TypeMapper;
import com.fh.model.*;
import com.fh.service.FlightService;
import com.fh.util.DataTableResult;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class FlightServiceImpl implements FlightService {
    @Autowired(required = false)
    private AreaMapper areaMapper;
    @Autowired(required = false)
    private FlightMapper flightMapper;
    @Autowired(required = false)
    private TicketMapper ticketMapper;
    @Autowired(required = false)
    private TypeMapper typeMapper;


    @Override
    public ServerResponse pageselect(FlightQuery flightQuery) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.and(StringUtils.isNotBlank(flightQuery.getName()),new Function<QueryWrapper<Flight>,QueryWrapper<Flight>>() {
            @Override
            public QueryWrapper<Flight> apply(QueryWrapper<Flight> productQueryWrapper) {
                QueryWrapper queryWrapper1=new QueryWrapper();
                String name = flightQuery.getName();
                List<Type> list=new ArrayList<>();
                String prefix=null;
                if(name.lastIndexOf("大型")!=-1){
                    queryWrapper1.eq("type",1);
                     prefix=name.split("大")[0];
                }
                if(name.lastIndexOf("中型")!=-1){
                    queryWrapper1.eq("type",2);
                     prefix=name.split("中")[0];
                }
                if(name.lastIndexOf("小型")!=-1){
                    queryWrapper1.eq("type",3);
                     prefix=name.split("小")[0];
                }
                if(name.lastIndexOf("大型")==-1&&name.lastIndexOf("中型")==-1&&name.lastIndexOf("小型")==-1){
                     prefix=name;
                }
                if(prefix!=null&&prefix.length()>0){
                    queryWrapper1.like("name",prefix);
                }
                List<Type> list1 = typeMapper.selectList(queryWrapper1);
                List<Integer> ids=new ArrayList<>();
                if(!list1.isEmpty()){
                    for (Type type : list1) {
                        ids.add(type.getId());
                    }
                }
                return productQueryWrapper.in(!ids.isEmpty(),"typeid",ids).or().like(prefix!=null&&prefix.length()>0,"name",prefix);
            }
        });
        queryWrapper.le(flightQuery.getStarttime()!=null,"starttime",flightQuery.getStarttime()==null?null:DateUtils.addHours(flightQuery.getStarttime(),1));
        queryWrapper.ge(flightQuery.getStarttime()!=null,"starttime",flightQuery.getStarttime()==null?null:DateUtils.addHours(flightQuery.getStarttime(),-1));
        QueryWrapper queryWrapper2=new QueryWrapper();
        queryWrapper2.eq(flightQuery.getTypeid()!=null,"type",flightQuery.getTypeid());
        if(flightQuery.getIdticket()!=null){
            queryWrapper2.gt(flightQuery.getIdticket()==0,"totalcount",0);
            queryWrapper2.eq(flightQuery.getIdticket()==1,"totalcount",0);
        }
        if(flightQuery.getTypeid()!=null||flightQuery.getIdticket()!=null){
            List<Ticket> ticketlist = ticketMapper.selectList(queryWrapper2);
            List<Integer> flightids=new ArrayList<>();
            if(!ticketlist.isEmpty()){
                for (Ticket ticket : ticketlist) {
                    flightids.add(ticket.getFlightid());
                }
            }
            if(ticketlist.isEmpty()){
                List li=new ArrayList();
                DataTableResult dataTableResult=new DataTableResult();
                dataTableResult.setDraw(flightQuery.getDraw());
                dataTableResult.setData(li);
                dataTableResult.setRecordsFiltered(0);
                dataTableResult.setRecordsTotal(0);
                return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
            }

            queryWrapper.in(!flightids.isEmpty(),"id",flightids);//返回一个票状态
        }

        QueryWrapper queryWrapper3=new QueryWrapper();
        queryWrapper3.eq(flightQuery.getType()!=null,"type",flightQuery.getType());
        queryWrapper3.eq(flightQuery.getTypename()!=null,"id",flightQuery.getTypename());
        if(flightQuery.getType()!=null||flightQuery.getTypename()!=null){
            List<Type> typelist = typeMapper.selectList(queryWrapper3);
            List<Integer> typeids=new ArrayList<>();
            if(!typelist.isEmpty()){
                for (Type type : typelist) {
                    typeids.add(type.getId());
                }
            }
            queryWrapper.in(!typeids.isEmpty(),"typeid",typeids);
        }



        if(flightQuery.getStartareaid()!=null){
            QueryWrapper queryWrapper4=new QueryWrapper();
            queryWrapper4.eq("pid",flightQuery.getStartareaid());
            Area area = areaMapper.selectOne(queryWrapper4);
            if(area!=null) {
                QueryWrapper queryWrapper8 = new QueryWrapper();
                queryWrapper8.eq( "pid", area.getId());
                List<Area> list = areaMapper.selectList(queryWrapper8);

                if(!list.isEmpty()){
                    List<Integer> ids=new ArrayList<>();
                    for (Area area1 : list) {
                        ids.add(area1.getId());
                    }
                        queryWrapper.in("startterminalid",ids);
                }

            }
            }

        if(flightQuery.getEndareaid()!=null){
            QueryWrapper queryWrapper5=new QueryWrapper();
            queryWrapper5.eq(flightQuery.getEndareaid()!=null,"pid",flightQuery.getEndareaid());
            Area area3 = areaMapper.selectOne(queryWrapper5);
            if(area3!=null) {
                QueryWrapper queryWrapper8 = new QueryWrapper();
                queryWrapper8.eq( "pid", area3.getId());
                List<Area> list = areaMapper.selectList(queryWrapper8);
                if(!list.isEmpty()){
                    List<Integer> ids=new ArrayList<>();
                    for (Area area1 : list) {
                        ids.add(area1.getId());
                    }
                    queryWrapper.in("endterminalid",ids);
                }
            }
        }
        Page<Flight> page=new Page<>((flightQuery.getStart()/flightQuery.getLength())+1,flightQuery.getLength());
        IPage iPage = flightMapper.selectPage(page, queryWrapper);
        List<Flight> records = iPage.getRecords();
        for (Flight record : records) {
            Type type = typeMapper.selectById(record.getTypeid());
            if(type!=null){
                record.setTypename(type.getName());
                record.setTypetype(type.getType()==1?"大型":type.getType()==2?"中型":"小型");
                record.setPlanename(record.getName()+"\t"+record.getTypename()+"("+record.getTypetype()+")");
            }
            Area area3 = areaMapper.selectById(record.getStartterminalid());
            Area area5 = areaMapper.selectById(area3.getPid());
            Area area4= areaMapper.selectById(record.getEndterminalid());
            Area area6 = areaMapper.selectById(area4.getPid());
            SimpleDateFormat sim=new SimpleDateFormat("HH:mm");
            String format = sim.format(record.getStarttime());
            String timess = DateUtils.addHours(new Date(), 2).compareTo(record.getStarttime()) > 0 ? "临近起飞" : "";
            String s5 = area5 != null ? area5.getName() : "";
            String starttimestr=format+timess+"\n"+s5+"\n"+(area3!=null?area3.getName():"");
            record.setStarttimestr(starttimestr);
            String s2= sim.format(record.getEndtime()) + (area6 != null ? area6.getName() : "");
            record.setEndtimestr(s2+(area4!=null?area4.getName():""));
            QueryWrapper queryWrapper6=new QueryWrapper();
            queryWrapper6.eq("flightid",record.getId());
            List<Ticket> ticketlist2 = ticketMapper.selectList(queryWrapper6);
             record.setTicketList(ticketlist2);
        }


        DataTableResult dataTableResult=new DataTableResult();
        dataTableResult.setDraw(flightQuery.getDraw());
        dataTableResult.setData(records);
        dataTableResult.setRecordsFiltered(iPage.getTotal());
        dataTableResult.setRecordsTotal(iPage.getTotal());
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
    }


    @Override
    public ServerResponse selectType() {
        List<Type> typeList = typeMapper.selectList(null);
        return ServerResponse.success(ResponseEnum.SUCCESS,typeList);
    }


    @Override
    public ServerResponse selectCity() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",1);
        List<Area> list = areaMapper.selectList(queryWrapper);
        return ServerResponse.success(ResponseEnum.SUCCESS,list);
    }


    @Override
    public ServerResponse addFlight(FlightInfo flightInfo) {
        Flight flight = flightInfo.getFlight();
        flightMapper.insert(flight);
        List<Ticket> ticketList = flightInfo.getTicketList();
        for (Ticket ticket : ticketList) {
            ticket.setFlightid(flight.getId());
            ticketMapper.insert(ticket);
        }
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }


    @Override
    public ServerResponse selectCityByPid(Integer pid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq(pid!=null,"pid",pid);
        List<Area> list = areaMapper.selectList(queryWrapper);
        return ServerResponse.success(ResponseEnum.SUCCESS,list);
    }

    @Override
    public ServerResponse selectname(String name) {
        if(StringUtils.isBlank(name)){
            ServerResponse.error(ResponseEnum.ERROR);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",name);
        Flight flight = flightMapper.selectOne(queryWrapper);
        if(flight!=null){
            return  ServerResponse.success(ResponseEnum.SUCCESS,1);
        }

        return ServerResponse.success(ResponseEnum.SUCCESS,2);
    }

    @Override
    public ServerResponse selectFlightById(Integer id) {
        Flight flight = flightMapper.selectById(id);
        Type type = typeMapper.selectById(flight.getTypeid());
        flight.setPlanename(flight.getName()+"-"+type.getName()+"("+
                (type.getType()==1?"大型":type.getType()==2?"中型":"小型")+")");
        String qifei=(DateUtils.addHours(new Date(),2).compareTo(flight.getStarttime())>0)?"临近起飞":"";
        SimpleDateFormat sim=new SimpleDateFormat("HH:mm");
        Area area = areaMapper.selectById(flight.getStartterminalid());
        String startarea=areaMapper.selectById(area.getPid()).getName()+area.getName();
        flight.setStarttimestr(sim.format(flight.getStarttime())+"\n"+qifei+"\n"+startarea);
        String daoda="时间:"+sim.format(flight.getEndtime());
        Area area2 = areaMapper.selectById(flight.getEndterminalid());
        String endarea=areaMapper.selectById(area2.getPid()).getName()+area2.getName();
        flight.setEndtimestr(daoda+"\n"+endarea);
        return ServerResponse.success(ResponseEnum.SUCCESS,flight);
    }


    @Override
    public ServerResponse updateprice(Integer flightid, Integer type) {
        if(flightid==null){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        if(type==null){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("flightid",flightid);
        queryWrapper.eq("type",type);
        Ticket ticket = ticketMapper.selectOne(queryWrapper);


        return ServerResponse.success(ResponseEnum.SUCCESS,ticket);
    }
}
