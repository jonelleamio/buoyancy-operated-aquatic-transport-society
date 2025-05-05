package com.kata.boats.domain.model;

import com.kata.boats.domain.exception.InvalidBoatUpdateInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record BoatInfoUpdate(UUID id, String name, String description) {
    public void validate() {
        validateId();
        validateName();
        validateDescription();
    }

    private void validateDescription() {
        if (StringUtils.isBlank(description)) {
            throw new InvalidBoatUpdateInfo("Boat description cannot be null or empty");
        }
    }

    private void validateName() {
        if (StringUtils.isBlank(name)) {
            throw new InvalidBoatUpdateInfo("Boat name cannot be null or empty");
        }
    }

    private void validateId() {
        if (id == null) {
            throw new InvalidBoatUpdateInfo("Boat ID cannot be null");
        }
    }

    public static Boat to(BoatInfoUpdate boat) {
        return new Boat(boat.id, boat.name, boat.description);
    }
}
