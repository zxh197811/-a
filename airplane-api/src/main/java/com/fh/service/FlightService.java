package com.fh.service;

import com.fh.model.Flight;
import com.fh.model.FlightInfo;
import com.fh.model.FlightQuery;
import com.fh.model.Ticket;
import com.fh.util.ServerResponse;

public interface FlightService {
    ServerResponse pageselect(FlightQuery flightQuery);

    ServerResponse selectType();

    ServerResponse selectCity();

    ServerResponse addFlight(FlightInfo flightInfo);

    ServerResponse selectCityByPid(Integer pid);

    ServerResponse selectname(String name);

    ServerResponse selectFlightById(Integer id);

    ServerResponse updateprice(Integer flightid, Integer type);
}
