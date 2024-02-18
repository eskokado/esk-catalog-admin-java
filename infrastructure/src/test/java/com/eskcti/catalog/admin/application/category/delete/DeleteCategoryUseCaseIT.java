package com.eskcti.catalog.admin.application.category.delete;

import com.eskcti.catalog.admin.IntegrationTest;
import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;


    @Test
    public void givenAValidId_whenCallsDeleteCategory_thenShouldReturnsBeOk() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistidas", true);
        final var expectedId = aCategory.getId();

        assertEquals(0, categoryRepository.count());

        save(aCategory);

        assertEquals(1, categoryRepository.count());

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCategory_thenShouldReturnsBeOk() {
        final var expectedId = CategoryID.from("123");

        assertEquals(0, categoryRepository.count());
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnsException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistidas", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryJpaEntity::from)
                        .toList()
        );
    }
}
