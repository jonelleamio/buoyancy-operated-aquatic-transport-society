package com.kata.boats.application;

import com.kata.boats.domain.exception.BoatNotFound;
import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatStringId;
import com.kata.boats.domain.port.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewBoatDetailsUseCaseTest {
    @Mock
    private BoatRepository boatRepository;
    @InjectMocks
    private ViewBoatDetailsUseCase viewBoatDetailsUseCase;

    @Test
    public void returns_boat_details_when_boat_exists() {
        // Given
        UUID givenBoatId = UUID.randomUUID();
        BoatStringId boatStringId = new BoatStringId(givenBoatId.toString());
        Boat expectedBoat = new Boat(
                givenBoatId,
                "The bo-at",
                "Buoyancy Operated Aquatic Transport"
        );
        when(boatRepository.findById(givenBoatId)).thenReturn(Optional.of(expectedBoat));

        // When
        Boat actual = viewBoatDetailsUseCase.execute(boatStringId);

        // Then
        assertThat(actual).isEqualTo(expectedBoat);
    }

    @Test
    public void returns_not_found_when_boat_does_not_exist() {
        // Given
        UUID givenBoatId = UUID.randomUUID();
        BoatStringId boatStringId = new BoatStringId(givenBoatId.toString());


        // When+Then
        assertThatThrownBy(() -> viewBoatDetailsUseCase.execute(boatStringId))
                .isInstanceOf(BoatNotFound.class)
                .hasMessage(String.format("Unable to find boat with id: %s", givenBoatId));
    }
}