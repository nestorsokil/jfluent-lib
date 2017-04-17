package com.jfluent.container;

import com.jfluent.exception.OperationFailureException;
import com.jfluent.function.CheckedConsumer;
import com.jfluent.function.CheckedFunction;
import com.jfluent.function.CheckedPredicate;
import com.jfluent.function.CheckedSupplier;

import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class Try<T> {
    private T result;
    private Throwable error;
    private boolean isSuccess;

    public static <T> Try<T> execute(CheckedSupplier<T> operation) {
        try {
            return new Try<>(operation.get());
        } catch (Throwable exc) {
            return new Try<>(exc);
        }
    }

    private Try(T val) {
        this.result = val;
        this.isSuccess = true;
    }

    private Try(Throwable exc) {
        this.error = exc;
        this.isSuccess = false;
    }

    public T getValue() throws OperationFailureException {
        if(isSuccess) {
            return result;
        } else {
            throw new OperationFailureException("Try object contains no value.", error);
        }
    }

    public Throwable getError() {
        return error;
    }

    public <R> Try<R> map(CheckedFunction<T, R> f) {
        return isSuccess ? execute(() -> f.apply(result)) : new Try<>(error);
    }

    public <R> Try<R> flatMap(Function<T, Try<R>> f) {
        return isSuccess ? f.apply(result) : new Try<>(error);
    }

    public Try<T> filter(CheckedPredicate<T> p) {
        if(!isSuccess)
            return this;
        try {
            return p.test(result) ? this : new Try<>(new NoSuchElementException("Predicate failure"));
        } catch (Throwable throwable){
            return new Try<>(throwable);
        }
    }

    public Try<T> ifSuccessful(CheckedConsumer<T> c) {
        if(isSuccess) {
            try {
                c.accept(result);
            } catch (Throwable err){
                return new Try<>(err);
            }
        }
        return this;
    }

    public Try<T> ifFailure(CheckedConsumer<Throwable> c) {
        if(!isSuccess)
            try {
                c.accept(error);
            } catch (Throwable err){
                return new Try<>(err);
            }
        return this;
    }

    public Option<T> option() {
        return isSuccess ? Option.of(result) : Option.empty();
    }
}
