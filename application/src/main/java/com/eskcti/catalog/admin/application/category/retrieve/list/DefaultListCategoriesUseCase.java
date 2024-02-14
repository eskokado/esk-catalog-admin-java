package com.eskcti.catalog.admin.application.category.retrieve.list;

import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(SearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
