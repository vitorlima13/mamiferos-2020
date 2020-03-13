package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ObservacaoInseto;
import com.mycompany.myapp.repository.ObservacaoInsetoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ObservacaoInseto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObservacaoInsetoResource {

    private final Logger log = LoggerFactory.getLogger(ObservacaoInsetoResource.class);

    private static final String ENTITY_NAME = "observacaoInseto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservacaoInsetoRepository observacaoInsetoRepository;

    public ObservacaoInsetoResource(ObservacaoInsetoRepository observacaoInsetoRepository) {
        this.observacaoInsetoRepository = observacaoInsetoRepository;
    }

    /**
     * {@code POST  /observacao-insetos} : Create a new observacaoInseto.
     *
     * @param observacaoInseto the observacaoInseto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observacaoInseto, or with status {@code 400 (Bad Request)} if the observacaoInseto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observacao-insetos")
    public ResponseEntity<ObservacaoInseto> createObservacaoInseto(@RequestBody ObservacaoInseto observacaoInseto) throws URISyntaxException {
        log.debug("REST request to save ObservacaoInseto : {}", observacaoInseto);
        if (observacaoInseto.getId() != null) {
            throw new BadRequestAlertException("A new observacaoInseto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservacaoInseto result = observacaoInsetoRepository.save(observacaoInseto);
        return ResponseEntity.created(new URI("/api/observacao-insetos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observacao-insetos} : Updates an existing observacaoInseto.
     *
     * @param observacaoInseto the observacaoInseto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observacaoInseto,
     * or with status {@code 400 (Bad Request)} if the observacaoInseto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observacaoInseto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observacao-insetos")
    public ResponseEntity<ObservacaoInseto> updateObservacaoInseto(@RequestBody ObservacaoInseto observacaoInseto) throws URISyntaxException {
        log.debug("REST request to update ObservacaoInseto : {}", observacaoInseto);
        if (observacaoInseto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObservacaoInseto result = observacaoInsetoRepository.save(observacaoInseto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observacaoInseto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observacao-insetos} : get all the observacaoInsetos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observacaoInsetos in body.
     */
    @GetMapping("/observacao-insetos")
    public List<ObservacaoInseto> getAllObservacaoInsetos() {
        log.debug("REST request to get all ObservacaoInsetos");
        return observacaoInsetoRepository.findAll();
    }

    /**
     * {@code GET  /observacao-insetos/:id} : get the "id" observacaoInseto.
     *
     * @param id the id of the observacaoInseto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observacaoInseto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observacao-insetos/{id}")
    public ResponseEntity<ObservacaoInseto> getObservacaoInseto(@PathVariable Long id) {
        log.debug("REST request to get ObservacaoInseto : {}", id);
        Optional<ObservacaoInseto> observacaoInseto = observacaoInsetoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(observacaoInseto);
    }

    /**
     * {@code DELETE  /observacao-insetos/:id} : delete the "id" observacaoInseto.
     *
     * @param id the id of the observacaoInseto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observacao-insetos/{id}")
    public ResponseEntity<Void> deleteObservacaoInseto(@PathVariable Long id) {
        log.debug("REST request to delete ObservacaoInseto : {}", id);
        observacaoInsetoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
