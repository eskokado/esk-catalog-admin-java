package com.eskcti.catalog.admin.infrastructure.api.controllers;

import com.eskcti.catalog.admin.application.category.create.CreateCategoryCommand;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryOutput;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.get.GetCatetoryByIdUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.eskcti.catalog.admin.application.category.update.UpdateCategoryCommand;
import com.eskcti.catalog.admin.application.category.update.UpdateCategoryOutput;
import com.eskcti.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.domain.pagination.SearchQuery;
import com.eskcti.catalog.admin.domain.validation.handler.Notification;
import com.eskcti.catalog.admin.infrastructure.api.CategoryAPI;
import com.eskcti.catalog.admin.infrastructure.category.models.CategoryListResponse;
import com.eskcti.catalog.admin.infrastructure.category.models.CategoryResponse;
import com.eskcti.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import com.eskcti.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import com.eskcti.catalog.admin.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCatetoryByIdUseCase getCatetoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;
    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCatetoryByIdUseCase getCatetoryByIdUseCase, UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCatetoryByIdUseCase = Objects.requireNonNull(getCatetoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(@RequestBody final CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(
                        URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            String search,
            int page,
            int perPage,
            String sort,
            String direction
    ) {
        return this.listCategoriesUseCase
                .execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCatetoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
