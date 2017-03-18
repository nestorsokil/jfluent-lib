package com.jfluent.function.checked;

/**
 * Created by nestorsokil on 17.03.2017.
 */
@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Throwable;
}
