package com.fh.service;

import com.fh.model.Area;
import com.fh.model.AreaQuery;
import com.fh.util.DataTableResult;
import com.fh.util.ServerResponse;

import java.util.List;

public interface AreaService {
    DataTableResult selectAreaBy(AreaQuery areaQuery);

    List<Area> selectArea();

    void addArea(Area area);

    Area selectAreaById(Integer id);

    void updateArea(Area area);

    void deleteArea(Integer id);

    void deleteAllArea(List<Integer> ids);
}
