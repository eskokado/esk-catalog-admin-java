package com.eskcti.catalog.admin.infrastructure.category;

import com.eskcti.catalog.admin.domain.category.Category;
import com.eskcti.catalog.admin.domain.category.CategoryGateway;
import com.eskcti.catalog.admin.domain.category.CategoryID;
import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.domain.pagination.SearchQuery;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import com.eskcti.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category aCategory) {
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        return null;
    }

    @Override
    public List<CategoryID> existsByIds(Iterable<CategoryID> ids) {
        return null;
    }
}
