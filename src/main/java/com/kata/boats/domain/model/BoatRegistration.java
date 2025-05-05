package com.kata.boats.domain.model;

import com.kata.boats.domain.exception.InvalidBoatRegistration;
import org.apache.commons.lang3.StringUtils;

public record BoatRegistration(String name, String description) {
    public void validate() {
        validateName();
        validateDescription();
    }

    private void validateDescription() {
        if (StringUtils.isBlank(description)) {
            throw new InvalidBoatRegistration("Boat description cannot be null or empty");
        }
    }

    private void validateName() {
        if (StringUtils.isBlank(name)) {
            throw new InvalidBoatRegistration("Boat name cannot be null or empty");
        }
    }

    public static Boat toBoat(BoatRegistration boat) {
        return new Boat(null, boat.name, boat.description);
    }
}
