package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ObservacaoAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ObservacaoAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservacaoAnimalRepository extends JpaRepository<ObservacaoAnimal, Long> {
}
