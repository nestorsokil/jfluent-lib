package com.jfluent.function;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface CheckedPredicate<T> {
    boolean test(T t) throws Throwable;
}
