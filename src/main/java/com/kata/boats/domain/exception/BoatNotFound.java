package com.kata.boats.domain.exception;

import static java.util.Objects.requireNonNull;

public class BoatNotFound extends RuntimeException {

    public BoatNotFound(String message) {
        super(requireNonNull(message));
    }
}
