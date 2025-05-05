package com.kata.boats.application;

import com.kata.boats.domain.model.Boat;
import com.kata.boats.domain.port.BoatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrowseBoatsUseCase {
   private final BoatRepository boatRepository;

    public Set<Boat> execute() {
        Set<Boat> boats = boatRepository.find();
        return CollectionUtils.isNotEmpty(boats) ? boats : Set.of();
    }
}
