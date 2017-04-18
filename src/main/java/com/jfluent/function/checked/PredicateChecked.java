package com.jfluent.function.checked;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface PredicateChecked<T> {
    boolean test(T t) throws Throwable;
}
