package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ObservacaoInseto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ObservacaoInseto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservacaoInsetoRepository extends JpaRepository<ObservacaoInseto, Long> {
}
