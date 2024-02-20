package com.eskcti.catalog.admin.infrastructure.category.presenters;

import com.eskcti.catalog.admin.application.category.retrieve.get.CategoryOutput;
import com.eskcti.catalog.admin.infrastructure.category.models.CategoryResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
