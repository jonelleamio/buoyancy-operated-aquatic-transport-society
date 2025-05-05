package com.kata.boats.infrastructure.out.jpa;

import com.kata.boats.infrastructure.out.dao.BoatDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoatJpaRepository extends JpaRepository<BoatDao, UUID> { }
