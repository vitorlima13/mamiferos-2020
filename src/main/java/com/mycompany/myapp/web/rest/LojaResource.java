package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Loja;
import com.mycompany.myapp.repository.LojaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Loja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LojaResource {

    private final Logger log = LoggerFactory.getLogger(LojaResource.class);

    private static final String ENTITY_NAME = "loja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LojaRepository lojaRepository;

    public LojaResource(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    /**
     * {@code POST  /lojas} : Create a new loja.
     *
     * @param loja the loja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loja, or with status {@code 400 (Bad Request)} if the loja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lojas")
    public ResponseEntity<Loja> createLoja(@RequestBody Loja loja) throws URISyntaxException {
        log.debug("REST request to save Loja : {}", loja);
        if (loja.getId() != null) {
            throw new BadRequestAlertException("A new loja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Loja result = lojaRepository.save(loja);
        return ResponseEntity.created(new URI("/api/lojas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lojas} : Updates an existing loja.
     *
     * @param loja the loja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loja,
     * or with status {@code 400 (Bad Request)} if the loja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lojas")
    public ResponseEntity<Loja> updateLoja(@RequestBody Loja loja) throws URISyntaxException {
        log.debug("REST request to update Loja : {}", loja);
        if (loja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Loja result = lojaRepository.save(loja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lojas} : get all the lojas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lojas in body.
     */
    @GetMapping("/lojas")
    public List<Loja> getAllLojas() {
        log.debug("REST request to get all Lojas");
        return lojaRepository.findAll();
    }

    /**
     * {@code GET  /lojas/:id} : get the "id" loja.
     *
     * @param id the id of the loja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lojas/{id}")
    public ResponseEntity<Loja> getLoja(@PathVariable Long id) {
        log.debug("REST request to get Loja : {}", id);
        Optional<Loja> loja = lojaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loja);
    }

    /**
     * {@code DELETE  /lojas/:id} : delete the "id" loja.
     *
     * @param id the id of the loja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lojas/{id}")
    public ResponseEntity<Void> deleteLoja(@PathVariable Long id) {
        log.debug("REST request to delete Loja : {}", id);
        lojaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
