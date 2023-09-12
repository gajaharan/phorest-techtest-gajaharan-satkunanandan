package com.phorest.exception;

import lombok.NonNull;

public final class DataNotFoundException extends RuntimeException {
    public <T> DataNotFoundException(@NonNull Class<T> entityType, @NonNull String identifier) {
        super("Data not found for type <%s> with identifier <%s>".formatted(entityType, identifier));
    }
}
