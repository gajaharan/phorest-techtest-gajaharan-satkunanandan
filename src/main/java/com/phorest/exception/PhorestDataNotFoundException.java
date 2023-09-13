package com.phorest.exception;

import lombok.NonNull;

public final class PhorestDataNotFoundException extends RuntimeException {
    public <T> PhorestDataNotFoundException(@NonNull Class<T> dataType, @NonNull String identifier) {
        super(String.format("Data not found for data type <%s> with identifier: %s", dataType, identifier));
    }
}
