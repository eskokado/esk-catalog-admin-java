package com.eskcti.catalog.admin.application.category.retrieve.get;

import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import com.eskcti.catalog.admin.domain.exceptions.DomainException;
import com.eskcti.catalog.admin.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCatetoryByIdUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var aCategoryID = CategoryID.from(anIn);

        return this.categoryGateway.findById(aCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(aCategoryID));
    }

    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
