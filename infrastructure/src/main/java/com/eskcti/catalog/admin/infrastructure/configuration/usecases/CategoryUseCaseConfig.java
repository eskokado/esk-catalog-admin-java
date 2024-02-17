package com.eskcti.catalog.admin.infrastructure.configuration.usecases;

import com.eskcti.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.create.DefaultCreateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.delete.DefaultDeleteCategoryUseCase;
import com.eskcti.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.get.GetCatetoryByIdUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.eskcti.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCatetoryByIdUseCase getCatetoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }
}