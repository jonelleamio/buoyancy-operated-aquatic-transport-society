package com.kata.boats.domain.port;

import com.kata.boats.domain.model.Boat;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BoatRepository {
    Set<Boat> find();

    Optional<Boat> findById(UUID id);

    Boat store(Boat boat);

    void remove(UUID id);
}
