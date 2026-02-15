package com.bdgarat.sbmscustomerservice.util;

public interface IMapper<T> {

    T getDto();

    void setData(T t);
}
