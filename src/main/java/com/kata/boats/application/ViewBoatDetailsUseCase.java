package com.kata.boats.application;

import com.kata.boats.domain.exception.BoatNotFound;
import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatStringId;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewBoatDetailsUseCase {
    private final BoatRepository boatRepository;

    public Boat execute(BoatStringId query) {
        query.validate();
        return boatRepository.findById(BoatStringId.to(query))
                .orElseThrow(() -> new BoatNotFound(String.format("Unable to find boat with id: %s", query.value())));
    }
}
