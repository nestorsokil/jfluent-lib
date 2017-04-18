package com.jfluent.function.simple;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
