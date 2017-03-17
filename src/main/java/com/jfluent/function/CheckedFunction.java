package com.jfluent.function;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface CheckedFunction<T,R> {
    R apply(T t) throws Throwable;
}
