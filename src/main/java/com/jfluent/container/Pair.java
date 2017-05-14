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

    public Pair(T first, U second) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return (first != null ? first.equals(pair.first) : pair.first == null)
                && (second != null ? second.equals(pair.second) : pair.second == null);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}