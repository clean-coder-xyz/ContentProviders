package com.cleancoder.base.common.util;

import com.google.common.base.Objects;

/**
 * Created by Leonid on 16.11.2014.
 */
class AbsentResult extends Result {

    private final Throwable exception;

    public AbsentResult(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public Object get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbsentResult)) {
            return false;
        }
        AbsentResult other = (AbsentResult) obj;
        return Objects.equal(getException(), other.getException());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getException());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("exception", getException())
                .toString();
    }
}
