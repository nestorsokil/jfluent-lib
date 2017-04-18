package com.jfluent.function.simple;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface TwoToUnit<T,V> {
    void consume(T t, V v);
}
