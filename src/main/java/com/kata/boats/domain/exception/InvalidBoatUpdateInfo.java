package com.kata.boats.domain.exception;

import static java.util.Objects.requireNonNull;

public class InvalidBoatUpdateInfo extends RuntimeException {

    public InvalidBoatUpdateInfo(String message) {
        super(requireNonNull(message));
    }
}
