package com.eskcti.catalog.admin.domain.exceptions;

import com.eskcti.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainException extends RuntimeException{
    protected final List<Error> errors;

    protected DomainException(final List<Error> anErros) {
        super("", null, true, false);
        this.errors = anErros;
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException(anErrors);
    }
    public List<Error> getErrors() {
        return errors;
    }
}
