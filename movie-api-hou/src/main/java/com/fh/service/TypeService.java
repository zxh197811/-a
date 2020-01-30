package com.fh.service;

import com.fh.model.Type;
import com.fh.model.TypeQuery;
import com.fh.util.DataTableResult;

import java.util.List;

public interface TypeService {
    DataTableResult selectTypeBy(TypeQuery typeQuery);

    List<Type> selectType();

    void addType(Type type);

    Type selectTypeById(Integer id);

    void updateType(Type type);

    void deleteType(Integer id);

    void deleteAllType(List<Integer> ids);
}
