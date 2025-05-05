package com.kata.boats.domain.exception;

import static java.util.Objects.requireNonNull;

public class InvalidBoatRegistration extends RuntimeException {

    public InvalidBoatRegistration(String message) {
        super(requireNonNull(message));
    }
}
