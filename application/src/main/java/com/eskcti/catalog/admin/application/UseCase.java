package com.eskcti.catalog.admin.application;

import com.eskcti.catalog.admin.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN anIn);
}