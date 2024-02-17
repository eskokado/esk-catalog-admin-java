package com.eskcti.catalog.admin.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {
    private SpecificationUtils() {}

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(prop)), like(term.toLowerCase()));
    }

    private static String like(final String term) {
        return "%" + term + "%";
    }
}
