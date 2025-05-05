package com.kata.boats.infrastructure.in.dto;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatInfoUpdate;
import com.kata.boats.domain.model.BoatRegistration;

import java.util.UUID;

public record BoatDto(UUID id, String name, String description) {
    public static BoatDto from(Boat boat) {
        return new BoatDto(boat.id(), boat.name(), boat.description());
    }

    public static BoatRegistration toBoatRegistration(BoatDto boatDto) {
        return new BoatRegistration(boatDto.name, boatDto.description);
    }

    public static BoatInfoUpdate toBoatInfoUpdate(BoatDto boatDto) {
        return new BoatInfoUpdate(boatDto.id, boatDto.name, boatDto.description);
    }
}