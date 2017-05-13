package com.jfluent.util;

import com.jfluent.container.Maybe;
import com.jfluent.container.Pair;
import com.jfluent.container.Result;
import com.jfluent.container.Triplet;
import com.jfluent.function.checked.UnitToOneChecked;
import com.jfluent.function.simple.UnitToOne;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nestorsokil on 17.04.2017.
 */
public class Prelude {
    private Prelude(){ }

    public static <T> Maybe<T> maybe(T value) {
        return Maybe.of(value);
    }

    public static <T> Maybe<T> maybe(UnitToOne<T> supplier) {
        return Maybe.of(supplier.supply());
    }

    @SafeVarargs
    public static <T> Maybe<T> maybeAny(T... nullableValues) {
        return Maybe.any(nullableValues);
    }

    public static <T> Result<T> result(UnitToOneChecked<T> unsafe){
        return Result.execute(unsafe);
    }

    public static <T, V> Pair <T, V> pair(T first, V second) {
        return new Pair<>(first, second);
    }

    public static <T, V, U> Triplet<T, V, U> triplet(T first, V second, U third) {
        return new Triplet<>(first, second, third);
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... values) {
        return Arrays.stream(values).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... values) {
        return Arrays.stream(values).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Pair<K, V>... values) {
        return Arrays.stream(values).collect(Collectors.toMap(Pair::first, Pair::second));
    }
}
