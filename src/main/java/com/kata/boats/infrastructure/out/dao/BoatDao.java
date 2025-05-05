package com.kata.boats.infrastructure.out.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "boats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoatDao {

    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
