package com.kata.boats.application;

import com.kata.boats.domain.model.BoatStringId;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBoatUseCase {
    private final BoatRepository boatRepository;

    public void execute(BoatStringId command) {
        command.validate();
        boatRepository.remove(BoatStringId.to(command));
    }
}
