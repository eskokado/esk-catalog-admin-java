package com.eskcti.catalog.admin.application.category.create;

import com.eskcti.catalog.admin.IntegrationTest;
import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        assertEquals(0, categoryRepository.count());

        final var aCommando =
                CreateCategoryCommand.with(
                        expectedName, expectedDescription, expectedIsActive
                );

        final var actualOutput = useCase.execute(aCommando).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        assertEquals(1, categoryRepository.count());

        final var actualCategory = categoryRepository.findById(actualOutput.id().getValue()).get();

        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());

    }

}
