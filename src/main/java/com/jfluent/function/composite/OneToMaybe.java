package com.jfluent.function.composite;

import com.jfluent.container.Maybe;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface OneToMaybe<T, R> {
    Maybe<R> apply(T t);
}
