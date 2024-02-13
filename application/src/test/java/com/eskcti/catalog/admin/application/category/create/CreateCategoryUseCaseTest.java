package com.eskcti.catalog.admin.application.category.create;

import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var aCommando =
                CreateCategoryCommand.with(
                        expectedName, expectedDescription, expectedIsActive
                );

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommando);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(
                argThat(
                        aCategory -> {
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.nonNull(aCategory.getId())
                                    && Objects.nonNull(aCategory.getCreatedAt())
                                    && Objects.nonNull(aCategory.getUpdatedAt())
                                    && Objects.isNull(aCategory.getDeletedAt());
                        }
                )
        );
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommando =
                CreateCategoryCommand.with(
                        expectedName, expectedDescription, expectedIsActive
                );

        final var actualException = assertThrows(
                DomainException.class, () -> useCase.execute(aCommando)
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).create(any());
    }
}
