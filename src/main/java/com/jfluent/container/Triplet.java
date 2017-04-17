package com.jfluent.container;

import com.jfluent.function.TripleConsumer;
import com.jfluent.function.TripleFunction;

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

    public <X,Y,Z> Triplet<X,Y,Z> flatMap(TripleFunction<T,U,V, Triplet<X,Y,Z>> function) {
        return function.apply(first, second, third);
    }

    public Triplet<T,U,V> apply(TripleConsumer<T,U,V> consumer) {
        consumer.accept(first, second, third);
        return this;
    }
}
