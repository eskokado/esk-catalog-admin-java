package com.eskcti.catalog.admin.domain.category;

import com.eskcti.catalog.admin.domain.AggregateRoot;
import com.eskcti.catalog.admin.domain.utils.InstantUtils;
import com.eskcti.catalog.admin.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> {
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant anUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreationDate;
        this.updatedAt = anUpdateDate;
        this.deletedAt = aDeleteDate;
    }

    public static Category newCategory(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.id,
                aCategory.name,
                aCategory.description,
                aCategory.active,
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }

    public static Category with(
            final CategoryID id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updateddAt,
            final Instant deletedAt
    ) {
        return new Category(
                id,
                name,
                description,
                active,
                createdAt,
                updateddAt,
                deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Category update(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
