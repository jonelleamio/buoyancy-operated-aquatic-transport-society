package com.kata.boats.domain.model;

import com.kata.boats.domain.exception.InvalidBoatDeletion;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record BoatStringId(String id) {
    public String value() {
        return id;
    }

    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new InvalidBoatDeletion("Boat ID cannot be null or empty");
        }
    }

    public static UUID to(BoatStringId boatStringId) {
        return UUID.fromString(boatStringId.value());
    }
}
