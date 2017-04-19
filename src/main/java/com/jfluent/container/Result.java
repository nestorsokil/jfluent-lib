package com.jfluent.container;

import com.jfluent.exception.OperationFailureException;
import com.jfluent.function.checked.OneToUnitChecked;
import com.jfluent.function.checked.OneToOneChecked;
import com.jfluent.function.checked.PredicateChecked;
import com.jfluent.function.checked.UnitToOneChecked;
import com.jfluent.function.composite.OneToResult;

import java.util.NoSuchElementException;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class Result<T> {
    private T result;
    private Throwable error;
    private boolean isSuccess;

    public static <T> Result<T> execute(UnitToOneChecked<T> operation) {
        try {
            return new Result<>(operation.get());
        } catch (Throwable exc) {
            return new Result<>(exc);
        }
    }

    private Result(T val) {
        this.result = val;
        this.isSuccess = true;
    }

    private Result(Throwable exc) {
        this.error = exc;
        this.isSuccess = false;
    }

    public T unwrap() throws OperationFailureException {
        if(isSuccess) {
            return result;
        } else {
            throw new OperationFailureException("Result object contains no value.", error);
        }
    }

    public Throwable unwrapError() {
        return error;
    }

    public <R> Result<R> map(OneToOneChecked<T, R> f) {
        return isSuccess ? execute(() -> f.apply(result)) : new Result<>(error);
    }

    public <R> Result<R> flatMap(OneToResult<T, R> f) {
        return isSuccess ? f.apply(result) : new Result<>(error);
    }

    public Result<T> filter(PredicateChecked<T> p) {
        if(!isSuccess)
            return this;
        try {
            return p.test(result) ? this : new Result<>(new NoSuchElementException("Predicate failure"));
        } catch (Throwable throwable){
            return new Result<>(throwable);
        }
    }

    public Result<T> ifSuccessful(OneToUnitChecked<T> c) {
        if(isSuccess) {
            try {
                c.accept(result);
            } catch (Throwable err){
                return new Result<>(err);
            }
        }
        return this;
    }

    public Result<T> ifFailure(OneToUnitChecked<Throwable> c) {
        if(!isSuccess)
            try {
                c.accept(error);
            } catch (Throwable err){
                return new Result<>(err);
            }
        return this;
    }

    public Maybe<T> maybe() {
        return isSuccess ? Maybe.of(result) : Maybe.empty();
    }
}
