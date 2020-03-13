package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mamifero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mamifero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MamiferoRepository extends JpaRepository<Mamifero, Long> {
}
