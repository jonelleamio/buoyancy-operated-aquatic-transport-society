package com.kata.boats.application;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.port.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrowseBoatsUseCaseTest {
    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BrowseBoatsUseCase browseBoatsUseCase;

    @Test
    public void returns_empty_list_when_no_boat_is_found() {
        // Given
        when(boatRepository.find()).thenReturn(Set.of());

        // When
        Set<Boat> result = browseBoatsUseCase.execute();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void returns_empty_list_when_null_is_returned() {
        // Given
        when(boatRepository.find()).thenReturn(null);

        // When
        Set<Boat> result = browseBoatsUseCase.execute();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void returns_set_of_boats_when_found() {
        // Given
        Boat b1 = new Boat(UUID.randomUUID(), "The bo-at", "Buoyancy Operated Aquatic Transport");
        Boat b2 = new Boat(UUID.randomUUID(), "Flynn Rider", "Candice's Speedboat");
        Set<Boat> boats = Set.of(b1, b2);
        when(boatRepository.find()).thenReturn(boats);

        // When
        Set<Boat> result = browseBoatsUseCase.execute();

        // Then
        assertThat(result).containsExactlyInAnyOrder(b1, b2);
    }
}