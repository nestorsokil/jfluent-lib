package com.jfluent.function.simple;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface ThreeToUnit<T,U,V> {
    void accept(T t, U u, V v);
}
