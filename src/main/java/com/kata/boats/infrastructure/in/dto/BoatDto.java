package com.kata.boats.infrastructure.in.dto;

import java.util.UUID;

public record BoatDto(UUID id, String name, String description) { }