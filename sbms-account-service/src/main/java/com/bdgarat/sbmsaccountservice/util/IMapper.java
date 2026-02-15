package com.bdgarat.sbmsaccountservice.util;

public interface IMapper<T> {

    T getDto();

    void setData(T t);
}
