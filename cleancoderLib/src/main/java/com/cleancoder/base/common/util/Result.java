package com.cleancoder.base.common.util;

/**
 * Created by Leonid on 16.11.2014.
 */
public abstract class Result<T> {

    public static <ResultType> Result<ResultType> get(ResultType result) {
        return new PresentResult(result);
    }

    public static <ResultType> Result<ResultType> absent(Throwable exception) {
        return new AbsentResult(exception);
    }

    Result() {
        // clients of the package can't extend this class
    }

    public abstract boolean isPresent();
    public abstract T get();
    public abstract Throwable getException();
}
