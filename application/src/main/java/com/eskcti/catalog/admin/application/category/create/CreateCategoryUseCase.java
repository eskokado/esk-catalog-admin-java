package com.eskcti.catalog.admin.application.category.create;

import com.eskcti.catalog.admin.application.UseCase;
import com.eskcti.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {

}
