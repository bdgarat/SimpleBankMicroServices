package com.bdgarat.sbmsaccountservice.util;

import java.util.List;

public interface ICrud<T> {

    List<T> getAll();

    T add(T t);

    T update(T t);

    void delete(T t);

    T getById(String id);

}
