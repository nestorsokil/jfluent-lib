package com.jfluent.container;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class Pair<T,U> {
    private T first;
    private U second;

    public static <T,U> Pair<T,U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    public <R,S> Pair<R,S> flatMap(BiFunction<T, U, Pair<R,S>> function) {
        return function.apply(first, second);
    }

    public Pair<T,U> apply(BiConsumer<T,U> consumer) {
        consumer.accept(first, second);
        return this;
    }
}