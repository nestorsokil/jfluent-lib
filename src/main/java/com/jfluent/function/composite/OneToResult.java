package com.jfluent.function.composite;

import com.jfluent.container.Result;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface OneToResult<T, R> {
    Result<R> apply(T t);
}
