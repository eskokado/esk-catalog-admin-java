package com.eskcti.catalog.admin.infrastructure.category.persistence;

import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.infrastructure.category.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_thenShouldReturnError() {
        final var expectedPropertyName = "name";
        final var expectedErrorMessage = "not-null property references a null or transient value : com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity.name";

        Category aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setName(null);

        final var actualException =
                Assertions.assertThrows(
                        DataIntegrityViolationException.class,
                        () -> categoryRepository.save(anEntity)
                );

        final var actualCause = Assertions.assertInstanceOf(
                PropertyValueException.class, actualException.getCause()
        );

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_thenShouldReturnError() {
        final var expectedPropertyName = "createdAt";
        final var expectedErrorMessage = "not-null property references a null or transient value : com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity.createdAt";

        Category aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException =
                Assertions.assertThrows(
                        DataIntegrityViolationException.class,
                        () -> categoryRepository.save(anEntity)
                );

        final var actualCause = Assertions.assertInstanceOf(
                PropertyValueException.class, actualException.getCause()
        );

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }
}
