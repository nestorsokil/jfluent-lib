package com.jfluent.function;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface TripleConsumer<T,U,V> {
    void accept(T t, U u, V v);
}
