package com.jfluent.container;

import java.util.function.Function;

/**
 * Created by nestorsokil on 18.03.2017.
 */
public class Triplet<T,U,V> {

    private T first;
    private U second;
    private V third;

    public static <T,U,V> Triplet<T,U,V> of(T first, U second, V third) {
        return new Triplet<>(first, second, third);
    }

    private Triplet(T first, U second, V third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    public V third() {
        return third;
    }

    public <X,Y,Z> Triplet<X, Y, Z> flatMap(Function<T,Function<U, Function<V, Triplet<X,Y,Z>>>> function) {
        return function.apply(first).apply(second).apply(third);
    }

    static Triplet<String,String,String> f(Integer a, Integer b, Integer c) {
        return Triplet.of(a.toString(), b.toString(), c.toString());
    }
}
