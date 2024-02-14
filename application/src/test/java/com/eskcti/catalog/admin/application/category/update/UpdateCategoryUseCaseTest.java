package com.eskcti.catalog.admin.application.category.update;

import com.eskcti.catalog.admin.application.category.create.CreateCategoryCommand;
import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId();

        final var aCommando =
                UpdateCategoryCommand.with(
                        expectedId.getValue(),
                        expectedName,
                        expectedDescription,
                        expectedIsActive
                );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommando).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(
                argThat(
                        anUpdateCategory -> {
                            return Objects.equals(expectedName, anUpdateCategory.getName())
                                    && Objects.equals(expectedDescription, anUpdateCategory.getDescription())
                                    && Objects.equals(expectedIsActive, anUpdateCategory.isActive())
                                    && Objects.equals(expectedId, anUpdateCategory.getId())
                                    && Objects.equals(aCategory.getCreatedAt(), anUpdateCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(anUpdateCategory.getUpdatedAt())
                                    && Objects.isNull(anUpdateCategory.getDeletedAt());
                        }
                )
        );
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        final var aCommando =
                UpdateCategoryCommand.with(
                        expectedId.getValue(),
                        expectedName,
                        expectedDescription,
                        expectedIsActive
                );


        final var notification = useCase.execute(aCommando).getLeft();

        assertEquals(expectedErrorMessage, notification.firstError().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_thenShouldReturnInactivateCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId();

        final var aCommando =
                UpdateCategoryCommand.with(
                        expectedId.getValue(),
                        expectedName,
                        expectedDescription,
                        expectedIsActive
                );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommando).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(
                argThat(
                        anUpdateCategory -> {
                            return Objects.equals(expectedName, anUpdateCategory.getName())
                                    && Objects.equals(expectedDescription, anUpdateCategory.getDescription())
                                    && Objects.equals(expectedIsActive, anUpdateCategory.isActive())
                                    && Objects.equals(expectedId, anUpdateCategory.getId())
                                    && Objects.equals(aCategory.getCreatedAt(), anUpdateCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(anUpdateCategory.getUpdatedAt())
                                    && Objects.nonNull(anUpdateCategory.getDeletedAt());
                        }
                )
        );
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;
        final var expectedId = aCategory.getId();

        final var aCommando =
                UpdateCategoryCommand.with(
                        expectedId.getValue(),
                        expectedName,
                        expectedDescription,
                        expectedIsActive
                );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommando).getLeft();

        assertEquals(expectedErrorMessage, notification.firstError().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(
                argThat(
                        anUpdateCategory -> {
                            return Objects.equals(expectedName, anUpdateCategory.getName())
                                    && Objects.equals(expectedDescription, anUpdateCategory.getDescription())
                                    && Objects.equals(expectedIsActive, anUpdateCategory.isActive())
                                    && Objects.equals(expectedId, anUpdateCategory.getId())
                                    && Objects.equals(aCategory.getCreatedAt(), anUpdateCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(anUpdateCategory.getUpdatedAt())
                                    && Objects.isNull(anUpdateCategory.getDeletedAt());
                        }
                )
        );
    }
}
