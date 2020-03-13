package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Mamifero;
import com.mycompany.myapp.repository.MamiferoRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Mamifero}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MamiferoResource {

    private final Logger log = LoggerFactory.getLogger(MamiferoResource.class);

    private static final String ENTITY_NAME = "mamifero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MamiferoRepository mamiferoRepository;

    public MamiferoResource(MamiferoRepository mamiferoRepository) {
        this.mamiferoRepository = mamiferoRepository;
    }

    /**
     * {@code POST  /mamiferos} : Create a new mamifero.
     *
     * @param mamifero the mamifero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mamifero, or with status {@code 400 (Bad Request)} if the mamifero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mamiferos")
    public ResponseEntity<Mamifero> createMamifero(@RequestBody Mamifero mamifero) throws URISyntaxException {
        log.debug("REST request to save Mamifero : {}", mamifero);
        if (mamifero.getId() != null) {
            throw new BadRequestAlertException("A new mamifero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mamifero result = mamiferoRepository.save(mamifero);
        return ResponseEntity.created(new URI("/api/mamiferos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mamiferos} : Updates an existing mamifero.
     *
     * @param mamifero the mamifero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mamifero,
     * or with status {@code 400 (Bad Request)} if the mamifero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mamifero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mamiferos")
    public ResponseEntity<Mamifero> updateMamifero(@RequestBody Mamifero mamifero) throws URISyntaxException {
        log.debug("REST request to update Mamifero : {}", mamifero);
        if (mamifero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mamifero result = mamiferoRepository.save(mamifero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mamifero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mamiferos} : get all the mamiferos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mamiferos in body.
     */
    @GetMapping("/mamiferos")
    public List<Mamifero> getAllMamiferos() {
        log.debug("REST request to get all Mamiferos");
        return mamiferoRepository.findAll();
    }

    /**
     * {@code GET  /mamiferos/:id} : get the "id" mamifero.
     *
     * @param id the id of the mamifero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mamifero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mamiferos/{id}")
    public ResponseEntity<Mamifero> getMamifero(@PathVariable Long id) {
        log.debug("REST request to get Mamifero : {}", id);
        Optional<Mamifero> mamifero = mamiferoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mamifero);
    }

    /**
     * {@code DELETE  /mamiferos/:id} : delete the "id" mamifero.
     *
     * @param id the id of the mamifero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mamiferos/{id}")
    public ResponseEntity<Void> deleteMamifero(@PathVariable Long id) {
        log.debug("REST request to delete Mamifero : {}", id);
        mamiferoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
