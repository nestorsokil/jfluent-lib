package com.jfluent.function;

/**
 * Created by nestorsokil on 17.04.2017.
 */
@FunctionalInterface
public interface TripleFunction<F,S,T,R> {
    R apply(F first, S second, T third);
}
