package com.kata.boats.application;

import com.kata.boats.domain.exception.InvalidBoatUpdateInfo;
import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatInfoUpdate;
import com.kata.boats.domain.port.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBoatInfoUseCaseTest {
    @Mock
    private BoatRepository boatRepository;
    @InjectMocks
    private UpdateBoatInfoUseCase updateBoatInfoUseCase;

    @Test
    public void returns_not_valid_when_boat_id_is_null() {
        // Given
        UUID boatId = null;
        String boatName = "The bo-at";
        String boatDescription = "Buoyancy Operated Aquatic Transport";
        BoatInfoUpdate command = new BoatInfoUpdate(boatId, boatName, boatDescription);

        // When+Then
        assertThatThrownBy(() -> updateBoatInfoUseCase.execute(command))
                .isInstanceOf(InvalidBoatUpdateInfo.class)
                .hasMessage("Boat ID cannot be null");
    }

    @Test
    public void returns_not_valid_when_boat_name_is_empty() {
        // Given
        UUID boatId = UUID.randomUUID();
        String boatName = "";
        String boatDescription = "Buoyancy Operated Aquatic Transport";
        BoatInfoUpdate command = new BoatInfoUpdate(boatId, boatName, boatDescription);

        // When+Then
        assertThatThrownBy(() -> updateBoatInfoUseCase.execute(command))
                .isInstanceOf(InvalidBoatUpdateInfo.class)
                .hasMessage("Boat name cannot be null or empty");
    }

    @Test
    public void returns_not_valid_when_boat_description_is_empty() {
        // Given
        UUID boatId = UUID.randomUUID();
        String boatName = "The bo-at";
        String boatDescription = "";
        BoatInfoUpdate command = new BoatInfoUpdate(boatId, boatName, boatDescription);

        // When+Then
        assertThatThrownBy(() -> updateBoatInfoUseCase.execute(command))
                .isInstanceOf(InvalidBoatUpdateInfo.class)
                .hasMessage("Boat description cannot be null or empty");
    }

    @Test
    public void updates_boat_info_when_boat_update_info_is_valid() {
        // Given
        UUID boatId = UUID.randomUUID();
        String boatName = "The bo-at";
        String boatDescription = "Buoyancy Operated Aquatic Transport";
        BoatInfoUpdate command = new BoatInfoUpdate(boatId, boatName, boatDescription);

        Boat expectedBoat = new Boat(boatId, boatName, boatDescription);
        when(boatRepository.store(BoatInfoUpdate.to(command))).thenReturn(expectedBoat);

        // When
        Boat actual = updateBoatInfoUseCase.execute(command);

        // Then
        assertThat(actual).isEqualTo(expectedBoat);
    }
}