package com.kata.boats.application;

import com.kata.boats.domain.model.BoatRegistration;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterBoatUseCase {
    private final BoatRepository boatRepository;

    public void execute(BoatRegistration command) {
        command.validate();
        boatRepository.store(BoatRegistration.toBoat(command));
    }
}
