package com.eskcti.catalog.admin.infrastructure.api.controllers;

import com.eskcti.catalog.admin.domain.pagination.Pagination;
import com.eskcti.catalog.admin.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {
    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, int sort, int direction) {
        return null;
    }
}
