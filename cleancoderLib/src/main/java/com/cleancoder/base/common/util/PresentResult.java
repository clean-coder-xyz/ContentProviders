package com.cleancoder.base.common.util;

import com.google.common.base.Objects;

/**
 * Created by Leonid on 16.11.2014.
 */
class PresentResult<T> extends Result<T> {

    private final T result;

    public PresentResult(T result) {
        this.result = result;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public T get() {
        return result;
    }

    @Override
    public Throwable getException() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PresentResult)) {
            return false;
        }
        PresentResult<?> other = (PresentResult<?>) obj;
        return Objects.equal(get(), other.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(get());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("result", get())
                .toString();
    }
}
