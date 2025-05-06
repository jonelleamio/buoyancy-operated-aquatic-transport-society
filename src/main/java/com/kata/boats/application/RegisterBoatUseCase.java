package com.kata.boats.application;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatRegistration;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterBoatUseCase {
    private final BoatRepository boatRepository;

    public Boat execute(BoatRegistration command) {
        command.validate();

        return boatRepository.store(BoatRegistration.toBoat(command));
    }
}
