package com.jfluent.container;

import com.jfluent.exception.EmptyValueException;
import com.jfluent.function.composite.OneToMaybe;
import com.jfluent.function.simple.OneToOne;
import com.jfluent.function.simple.OneToUnit;
import com.jfluent.function.simple.Predicate;
import com.jfluent.function.simple.UnitToOne;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class Maybe<T> {
    private static final Maybe<?> EMPTY = new Maybe<>();
    private T value;
    private boolean isEmpty;

    private Maybe(T value) {
        this.value = value;
        this.isEmpty = false;
    }

    private Maybe() {
        this.value = null;
        this.isEmpty = true;
    }

    public static <T> Maybe<T> of(T value) {
        return value == null ? empty() : new Maybe<>(value);
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "ConstantConditions"})
    public static <T> Maybe<T> fromOption(Optional<T> option) {
        return of(option.get());
    }

    @SafeVarargs
    public static <T> Maybe<T> any(T... values) {
        return fromOption(Stream.of(values).filter(Objects::nonNull).findFirst());
    }

    @SuppressWarnings("unchecked")
    public static<T> Maybe<T> empty() {
        return (Maybe<T>) EMPTY;
    }

    public T unwrap() throws EmptyValueException {
        return orElseThrow(() -> new EmptyValueException("Empty.unwrap()"));
    }

    public T unwrapOr(T other) {
        return isEmpty ? other : value;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isPresent() {
        return !isEmpty;
    }

    public final <R> Maybe<R> or(UnitToOne<R> alternative) {
        return isEmpty ? Maybe.of(alternative.supply()) : empty();
    }

    public final <R> Maybe<R> or(R alternative) {
        return isEmpty ? Maybe.of(alternative) : empty();
    }

    public final <R> Maybe<R> map(OneToOne<T, R> f){
        return isEmpty ? Maybe.of(null) : Maybe.of(f.apply(value));
    }

    public final <R> Maybe<R> flatMap(OneToMaybe<T, R> f) {
        return isEmpty ? empty() : f.apply(value);
    }

    public final Maybe<T> filter(Predicate<T> p) {
        return (isEmpty || p.test(value)) ? this : empty();
    }

    public final Maybe<T> filterNot(Predicate<T> p) {
        return (isEmpty || !p.test(value)) ? this : empty();
    }

    public final T orNull() {
        return isEmpty ? null : value;
    }

    public Result<T> orError(UnitToOne<Throwable> errorSupplier) {
        if(isEmpty) {
            return Result.execute(() -> {
                throw errorSupplier.supply();
            });
        }
        return Result.execute(this::unwrap);
    }

    public Maybe<T> ifEmpty(Runnable r) {
        if(isEmpty) r.run();
        return this;
    }

    public Maybe<T> ifPresent(Runnable r) {
        if(!isEmpty) r.run();
        return this;
    }

    public Maybe<T> ifPresent(OneToUnit<T> c) {
        if(!isEmpty) c.consume(value);
        return this;
    }

    public boolean matches(Predicate<T> p) {
        return !isEmpty && p.test(value);
    }

    public <X extends Throwable> T orElseThrow(UnitToOne<? extends X> exceptionSupplier) throws X {
        if(!isEmpty) {
            return value;
        } else {
            throw exceptionSupplier.supply();
        }
    }

    public Stream<T> stream() {
        return isEmpty ? Stream.empty() : Stream.of(value);
    }

    @Override
    public String toString() {
        return isEmpty ? "Empty maybe" : String.format("Maybe[%s]", value.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!this.getClass().equals(obj.getClass())) return false;
        Maybe that = (Maybe) obj;
        return !this.isEmpty ? this.value.equals(that.value) : that.isEmpty;
    }

    @Override
    public int hashCode() {
        return isEmpty ? 0 : value.hashCode();
    }
}