package com.eskcti.catalog.admin.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, String> {
}
