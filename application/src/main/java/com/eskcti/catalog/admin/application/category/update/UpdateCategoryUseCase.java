package com.eskcti.catalog.admin.application.category.update;

import com.eskcti.catalog.admin.application.UseCase;
import com.eskcti.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand,
        Either<Notification, UpdateCategoryOutput>> {
}
