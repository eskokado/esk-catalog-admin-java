package com.eskcti.catalog.admin.application.category.delete;

import com.eskcti.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase;
import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {
    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_thenShouldReturnsBeOk() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistidas", true);
        final var expectedId = aCategory.getId();

        doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }


    @Test
    public void givenAnInvalidId_whenCallsDeleteCategory_thenShouldReturnsBeOk() {
        final var expectedId = CategoryID.from("123");

        doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

}
