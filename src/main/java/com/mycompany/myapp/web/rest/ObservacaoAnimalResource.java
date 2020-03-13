package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ObservacaoAnimal;
import com.mycompany.myapp.repository.ObservacaoAnimalRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ObservacaoAnimal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObservacaoAnimalResource {

    private final Logger log = LoggerFactory.getLogger(ObservacaoAnimalResource.class);

    private static final String ENTITY_NAME = "observacaoAnimal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservacaoAnimalRepository observacaoAnimalRepository;

    public ObservacaoAnimalResource(ObservacaoAnimalRepository observacaoAnimalRepository) {
        this.observacaoAnimalRepository = observacaoAnimalRepository;
    }

    /**
     * {@code POST  /observacao-animals} : Create a new observacaoAnimal.
     *
     * @param observacaoAnimal the observacaoAnimal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observacaoAnimal, or with status {@code 400 (Bad Request)} if the observacaoAnimal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observacao-animals")
    public ResponseEntity<ObservacaoAnimal> createObservacaoAnimal(@RequestBody ObservacaoAnimal observacaoAnimal) throws URISyntaxException {
        log.debug("REST request to save ObservacaoAnimal : {}", observacaoAnimal);
        if (observacaoAnimal.getId() != null) {
            throw new BadRequestAlertException("A new observacaoAnimal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservacaoAnimal result = observacaoAnimalRepository.save(observacaoAnimal);
        return ResponseEntity.created(new URI("/api/observacao-animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observacao-animals} : Updates an existing observacaoAnimal.
     *
     * @param observacaoAnimal the observacaoAnimal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observacaoAnimal,
     * or with status {@code 400 (Bad Request)} if the observacaoAnimal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observacaoAnimal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observacao-animals")
    public ResponseEntity<ObservacaoAnimal> updateObservacaoAnimal(@RequestBody ObservacaoAnimal observacaoAnimal) throws URISyntaxException {
        log.debug("REST request to update ObservacaoAnimal : {}", observacaoAnimal);
        if (observacaoAnimal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObservacaoAnimal result = observacaoAnimalRepository.save(observacaoAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observacaoAnimal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observacao-animals} : get all the observacaoAnimals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observacaoAnimals in body.
     */
    @GetMapping("/observacao-animals")
    public List<ObservacaoAnimal> getAllObservacaoAnimals() {
        log.debug("REST request to get all ObservacaoAnimals");
        return observacaoAnimalRepository.findAll();
    }

    /**
     * {@code GET  /observacao-animals/:id} : get the "id" observacaoAnimal.
     *
     * @param id the id of the observacaoAnimal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observacaoAnimal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observacao-animals/{id}")
    public ResponseEntity<ObservacaoAnimal> getObservacaoAnimal(@PathVariable Long id) {
        log.debug("REST request to get ObservacaoAnimal : {}", id);
        Optional<ObservacaoAnimal> observacaoAnimal = observacaoAnimalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(observacaoAnimal);
    }

    /**
     * {@code DELETE  /observacao-animals/:id} : delete the "id" observacaoAnimal.
     *
     * @param id the id of the observacaoAnimal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observacao-animals/{id}")
    public ResponseEntity<Void> deleteObservacaoAnimal(@PathVariable Long id) {
        log.debug("REST request to delete ObservacaoAnimal : {}", id);
        observacaoAnimalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
