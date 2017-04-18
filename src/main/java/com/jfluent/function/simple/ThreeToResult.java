package com.jfluent.function.simple;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface ThreeToResult<F,S,T,R> {
    R apply(F first, S second, T third);
}

