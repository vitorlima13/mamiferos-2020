package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Inseto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inseto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsetoRepository extends JpaRepository<Inseto, Long> {
}
