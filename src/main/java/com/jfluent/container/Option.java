package com.jfluent.container;

import com.jfluent.exception.EmptyValueException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class Option<T> {
    private static final Option<?> EMPTY = new Option<>();
    private T value;
    private boolean isEmpty;

    private Option(T value) {
        this.value = value;
        this.isEmpty = false;
    }

    private Option() {
        this.value = null;
        this.isEmpty = true;
    }

    public static <T> Option<T> of(T value) {
        return value == null ? empty() : new Option<>(value);
    }

    @SuppressWarnings("unchecked")
    public static<T> Option<T> empty() {
        return (Option<T>) EMPTY;
    }

    public T get() throws EmptyValueException {
        return orElseThrow(() -> new EmptyValueException("Empty.get()"));
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public final <R> Option<R> or(Supplier<R> alternative) {
        return isEmpty() ? Option.of(alternative.get()) : empty();
    }

    public final <R> Option<R> or(R alternative) {
        return isEmpty() ? Option.of(alternative) : empty();
    }

    public final <R> Option<R> map(Function<T, R> f){
        return isEmpty() ? Option.of(null) : Option.of(f.apply(value));
    }

    public final <R> Option<R> flatMap(Function<T, Option<R>> f) {
        return isEmpty() ? empty() : f.apply(value);
    }

    public final Option<T> filter(Predicate<T> p) {
        return (isEmpty() || p.test(value)) ? this : empty();
    }

    public final Option<T> filterNot(Predicate<T> p) {
        return (isEmpty() || !p.test(value)) ? this : empty();
    }

    public T orNull() {
        return isEmpty() ? null : value;
    }

    public Try<T> orError(Supplier<Throwable> errorSupplier) {
        if(isEmpty()) {
            return Try.execute(() -> {
                throw errorSupplier.get();
            });
        }
        return Try.execute(this::get);
    }

    public Option<T> ifEmpty(Runnable r) {
        if(isEmpty()) r.run();
        return this;
    }

    public Option<T> ifPresent(Runnable r) {
        if(!isEmpty()) r.run();
        return this;
    }

    public Option<T> ifPresent(Consumer<T> c) {
        if(!isEmpty()) c.accept(value);
        return this;
    }

    public boolean matches(Predicate<T> p) {
        return !isEmpty() && p.test(value);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if(!isEmpty()) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Stream<T> stream() {
        return isEmpty() ? Stream.empty() : Stream.of(value);
    }

    @Override
    public String toString() {
        return isEmpty() ? "Empty option" : String.format("Option[%s]", value.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!this.getClass().equals(obj.getClass())) return false;
        Option that = (Option) obj;
        return !this.isEmpty() ? this.value.equals(that.value) : that.isEmpty();
    }

    @Override
    public int hashCode() {
        return isEmpty() ? 0 : value.hashCode();
    }
}