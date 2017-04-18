package com.jfluent.function.checked;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface OneToOneChecked<T,R> {
    R apply(T t) throws Throwable;
}
