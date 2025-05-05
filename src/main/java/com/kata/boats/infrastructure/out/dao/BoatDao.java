package com.kata.boats.infrastructure.out.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

import java.util.UUID;

@Entity(name = "boat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoatDao {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
