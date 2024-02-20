package com.eskcti.catalog.admin.application.category.retrieve.get;

import com.eskcti.catalog.admin.IntegrationTest;
import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import com.eskcti.catalog.admin.domain.exceptions.NotFoundException;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class GetCategoryByIdUseCaseIT {
    @Autowired
    private GetCatetoryByIdUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsGetCategory_thenShouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        assertEquals(0, categoryRepository.count());

        save(aCategory);

        assertEquals(1, categoryRepository.count());

        final var actualCategory = useCase.execute(expectedId.getValue());

        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(aCategory.getCreatedAt().truncatedTo(ChronoUnit.SECONDS), actualCategory.createdAt().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(aCategory.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS), actualCategory.updatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertNull(actualCategory.deletedAt());
    }


    @Test
    public void givenAnInvalidId_whenCallGetCategory_thenShouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 0;

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Gateway message";
        final var expectedErrorCount = 1;

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).findById(eq(expectedId));

        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryJpaEntity::from)
                        .toList()
        );
    }
}
