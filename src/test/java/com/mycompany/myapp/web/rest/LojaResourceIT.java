package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Mamiferos2020App;
import com.mycompany.myapp.domain.Loja;
import com.mycompany.myapp.repository.LojaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LojaResource} REST controller.
 */
@SpringBootTest(classes = Mamiferos2020App.class)

@AutoConfigureMockMvc
@WithMockUser
public class LojaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLojaMockMvc;

    private Loja loja;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loja createEntity(EntityManager em) {
        Loja loja = new Loja()
            .nome(DEFAULT_NOME);
        return loja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loja createUpdatedEntity(EntityManager em) {
        Loja loja = new Loja()
            .nome(UPDATED_NOME);
        return loja;
    }

    @BeforeEach
    public void initTest() {
        loja = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoja() throws Exception {
        int databaseSizeBeforeCreate = lojaRepository.findAll().size();

        // Create the Loja
        restLojaMockMvc.perform(post("/api/lojas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loja)))
            .andExpect(status().isCreated());

        // Validate the Loja in the database
        List<Loja> lojaList = lojaRepository.findAll();
        assertThat(lojaList).hasSize(databaseSizeBeforeCreate + 1);
        Loja testLoja = lojaList.get(lojaList.size() - 1);
        assertThat(testLoja.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createLojaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lojaRepository.findAll().size();

        // Create the Loja with an existing ID
        loja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLojaMockMvc.perform(post("/api/lojas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loja)))
            .andExpect(status().isBadRequest());

        // Validate the Loja in the database
        List<Loja> lojaList = lojaRepository.findAll();
        assertThat(lojaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLojas() throws Exception {
        // Initialize the database
        lojaRepository.saveAndFlush(loja);

        // Get all the lojaList
        restLojaMockMvc.perform(get("/api/lojas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loja.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getLoja() throws Exception {
        // Initialize the database
        lojaRepository.saveAndFlush(loja);

        // Get the loja
        restLojaMockMvc.perform(get("/api/lojas/{id}", loja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loja.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingLoja() throws Exception {
        // Get the loja
        restLojaMockMvc.perform(get("/api/lojas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoja() throws Exception {
        // Initialize the database
        lojaRepository.saveAndFlush(loja);

        int databaseSizeBeforeUpdate = lojaRepository.findAll().size();

        // Update the loja
        Loja updatedLoja = lojaRepository.findById(loja.getId()).get();
        // Disconnect from session so that the updates on updatedLoja are not directly saved in db
        em.detach(updatedLoja);
        updatedLoja
            .nome(UPDATED_NOME);

        restLojaMockMvc.perform(put("/api/lojas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoja)))
            .andExpect(status().isOk());

        // Validate the Loja in the database
        List<Loja> lojaList = lojaRepository.findAll();
        assertThat(lojaList).hasSize(databaseSizeBeforeUpdate);
        Loja testLoja = lojaList.get(lojaList.size() - 1);
        assertThat(testLoja.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingLoja() throws Exception {
        int databaseSizeBeforeUpdate = lojaRepository.findAll().size();

        // Create the Loja

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLojaMockMvc.perform(put("/api/lojas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loja)))
            .andExpect(status().isBadRequest());

        // Validate the Loja in the database
        List<Loja> lojaList = lojaRepository.findAll();
        assertThat(lojaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoja() throws Exception {
        // Initialize the database
        lojaRepository.saveAndFlush(loja);

        int databaseSizeBeforeDelete = lojaRepository.findAll().size();

        // Delete the loja
        restLojaMockMvc.perform(delete("/api/lojas/{id}", loja.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Loja> lojaList = lojaRepository.findAll();
        assertThat(lojaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
