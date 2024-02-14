package com.eskcti.catalog.admin.application.category.delete;

import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(final String anIn) {
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
