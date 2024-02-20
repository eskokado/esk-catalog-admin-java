package com.eskcti.catalog.admin.infrastructure.api.controllers;

import com.eskcti.catalog.admin.application.category.create.CreateCategoryCommand;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryOutput;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.get.GetCatetoryByIdUseCase;
import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.domain.validation.handler.Notification;
import com.eskcti.catalog.admin.infrastructure.api.CategoryAPI;
import com.eskcti.catalog.admin.infrastructure.category.models.CategoryResponse;
import com.eskcti.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
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

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCatetoryByIdUseCase getCatetoryByIdUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCatetoryByIdUseCase = Objects.requireNonNull(getCatetoryByIdUseCase);
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
    public Pagination<?> listCategories(String search, int page, int perPage, int sort, int direction) {
        return null;
    }

    @Override
    public CategoryResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCatetoryByIdUseCase.execute(id));
    }
}
