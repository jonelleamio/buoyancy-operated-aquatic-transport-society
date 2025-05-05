package com.kata.boats.application;

import com.kata.boats.domain.exception.InvalidBoatRegistration;
import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.model.BoatRegistration;
import com.kata.boats.domain.port.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterBoatUseCaseTest {
    @Mock
    private BoatRepository boatRepository;
    @InjectMocks
    private RegisterBoatUseCase registerBoatUseCase;

    @Test
    public void returns_not_valid_when_boat_name_is_empty() {
        // Given
        String boatName = "";
        String boatDescription = "Buoyancy Operated Aquatic Transport";
        BoatRegistration command = new BoatRegistration(boatName, boatDescription);

        // When+Then
        assertThatThrownBy(() -> registerBoatUseCase.execute(command))
                .isInstanceOf(InvalidBoatRegistration.class)
                .hasMessage("Boat name cannot be null or empty");
    }

    @Test
    public void returns_not_valid_when_boat_description_is_empty() {
        // Given
        String boatName = "The bo-at";
        String boatDescription = "";
        BoatRegistration command = new BoatRegistration(boatName, boatDescription);

        // When+Then
        assertThatThrownBy(() -> registerBoatUseCase.execute(command))
                .isInstanceOf(InvalidBoatRegistration.class)
                .hasMessage("Boat description cannot be null or empty");
    }

    @Test
    public void registers_boat_when_boat_registration_is_valid() {
        // Given
        String boatName = "The bo-at";
        String boatDescription = "Buoyancy Operated Aquatic Transport";
        BoatRegistration command = new BoatRegistration(boatName, boatDescription);
        Boat expectedBoat = new Boat(null, boatName, boatDescription);

        // When
        registerBoatUseCase.execute(command);

        // Then
        assertTrue(true);
        verify(boatRepository).store(expectedBoat);
    }
}