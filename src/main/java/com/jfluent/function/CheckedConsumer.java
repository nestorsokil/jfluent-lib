package com.jfluent.function;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface CheckedConsumer<T> {
    void accept(T t) throws Throwable;
}
