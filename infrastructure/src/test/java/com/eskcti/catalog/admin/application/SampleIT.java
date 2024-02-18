package com.eskcti.catalog.admin.application;

import com.eskcti.catalog.admin.IntegrationTest;
import com.eskcti.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void testInject() {
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }
}
