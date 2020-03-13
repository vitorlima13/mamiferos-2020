package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Loja;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Loja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
}
