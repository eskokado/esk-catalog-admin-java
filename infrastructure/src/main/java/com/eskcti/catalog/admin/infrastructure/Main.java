package com.eskcti.catalog.admin.infrastructure;

import com.eskcti.catalog.admin.application.UseCase;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.eskcti.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.get.GetCatetoryByIdUseCase;
import com.eskcti.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.eskcti.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.eskcti.catalog.admin.infrastructure.configuration.WebServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

//    @Bean
//    @DependsOnDatabaseInitialization
//    ApplicationRunner runner(
//            @Autowired CreateCategoryUseCase createCategoryUseCase,
//            @Autowired UpdateCategoryUseCase updateCategoryUseCase,
//            @Autowired DeleteCategoryUseCase deleteCategoryUseCase,
//            @Autowired GetCatetoryByIdUseCase getCatetoryByIdUseCase,
//            @Autowired ListCategoriesUseCase listCategoriesUseCase) {
//        return args -> {
//
//        };
//    }
}