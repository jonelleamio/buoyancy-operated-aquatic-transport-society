package com.kata.boats.application;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatInfoUpdate;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBoatInfoUseCase {
    private final BoatRepository boatRepository;

    public Boat execute(BoatInfoUpdate command) {
        command.validate();
        return boatRepository.store(BoatInfoUpdate.to(command));
    }
}
