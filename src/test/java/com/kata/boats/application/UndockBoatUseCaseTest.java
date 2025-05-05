package com.kata.boats.application;

import com.kata.boats.domain.exception.InvalidBoatDeletion;
import com.kata.boats.domain.model.BoatStringId;
import com.kata.boats.domain.port.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UndockBoatUseCaseTest {
    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private UndockBoatUseCase undockBoatUseCase;

    @Test
    public void returns_not_valid_when_boat_id_is_empty() {
        // Given
        BoatStringId boatStringId = new BoatStringId("");

        // When+Then
        assertThatThrownBy(() -> undockBoatUseCase.execute(boatStringId))
                .isInstanceOf(InvalidBoatDeletion.class)
                .hasMessage("Boat ID cannot be null or empty");
    }

    @Test
    public void deletes_boat_when_boat_id_is_valid() {
        // Given
        BoatStringId boatStringId = new BoatStringId(UUID.randomUUID().toString());

        // When
        undockBoatUseCase.execute(boatStringId);

        // Then
        verify(boatRepository).remove(BoatStringId.to(boatStringId));
    }
}