package com.eskcti.catalog.admin.application.category.retrieve.list;

import com.eskcti.catalog.admin.application.UseCase;
import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListCategoriesUseCase
    extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
