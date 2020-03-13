package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Inseto;
import com.mycompany.myapp.repository.InsetoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Inseto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InsetoResource {

    private final Logger log = LoggerFactory.getLogger(InsetoResource.class);

    private static final String ENTITY_NAME = "inseto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsetoRepository insetoRepository;

    public InsetoResource(InsetoRepository insetoRepository) {
        this.insetoRepository = insetoRepository;
    }

    /**
     * {@code POST  /insetos} : Create a new inseto.
     *
     * @param inseto the inseto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inseto, or with status {@code 400 (Bad Request)} if the inseto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insetos")
    public ResponseEntity<Inseto> createInseto(@RequestBody Inseto inseto) throws URISyntaxException {
        log.debug("REST request to save Inseto : {}", inseto);
        if (inseto.getId() != null) {
            throw new BadRequestAlertException("A new inseto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inseto result = insetoRepository.save(inseto);
        return ResponseEntity.created(new URI("/api/insetos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insetos} : Updates an existing inseto.
     *
     * @param inseto the inseto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inseto,
     * or with status {@code 400 (Bad Request)} if the inseto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inseto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insetos")
    public ResponseEntity<Inseto> updateInseto(@RequestBody Inseto inseto) throws URISyntaxException {
        log.debug("REST request to update Inseto : {}", inseto);
        if (inseto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inseto result = insetoRepository.save(inseto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inseto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insetos} : get all the insetos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insetos in body.
     */
    @GetMapping("/insetos")
    public List<Inseto> getAllInsetos() {
        log.debug("REST request to get all Insetos");
        return insetoRepository.findAll();
    }

    /**
     * {@code GET  /insetos/:id} : get the "id" inseto.
     *
     * @param id the id of the inseto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inseto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insetos/{id}")
    public ResponseEntity<Inseto> getInseto(@PathVariable Long id) {
        log.debug("REST request to get Inseto : {}", id);
        Optional<Inseto> inseto = insetoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inseto);
    }

    /**
     * {@code DELETE  /insetos/:id} : delete the "id" inseto.
     *
     * @param id the id of the inseto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insetos/{id}")
    public ResponseEntity<Void> deleteInseto(@PathVariable Long id) {
        log.debug("REST request to delete Inseto : {}", id);
        insetoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
