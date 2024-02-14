package com.eskcti.catalog.admin.application.category.retrieve.get;

import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import com.eskcti.catalog.admin.domain.exceptions.DomainException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {
    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;
    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_thenShouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        final var actualCategory = useCase.execute(expectedId.getValue());

        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
        assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
    }

    @Test
    public void givenAnInvalidId_whenCallGetCategory_thenShouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 1;

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());


        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
