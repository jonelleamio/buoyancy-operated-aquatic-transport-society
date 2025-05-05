package com.kata.boats.infrastructure.out;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.port.BoatRepository;
import com.kata.boats.infrastructure.out.dao.BoatDao;
import com.kata.boats.infrastructure.out.jpa.BoatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostgresBoatRepository implements BoatRepository {
    private final BoatJpaRepository jpa;

    private static Boat toBoat(BoatDao boat) {
        return new Boat(boat.getId(), boat.getName(), boat.getDescription());
    }

    private static BoatDao fromBoat(Boat boat) {
        return new BoatDao(boat.id(), boat.name(), boat.description());
    }

    @Override
    public Set<Boat> find() {
        return jpa.findAll()
                .stream()
                .map(PostgresBoatRepository::toBoat)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Boat> findById(UUID id) {
        return jpa.findById(id).map(PostgresBoatRepository::toBoat);
    }

    @Override
    public Boat store(Boat boat) {
        return toBoat(jpa.save(fromBoat(boat)));
    }

    @Override
    public void remove(UUID id) {
        jpa.deleteById(id);
    }
}
