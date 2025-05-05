package com.kata.boats.domain.exception;

import static java.util.Objects.requireNonNull;

public class InvalidBoatDeletion extends RuntimeException {
    public InvalidBoatDeletion(String message) {
        super(requireNonNull(message));
    }
}
