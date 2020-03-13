package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Mamiferos2020App;
import com.mycompany.myapp.domain.Mamifero;
import com.mycompany.myapp.repository.MamiferoRepository;

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

import com.mycompany.myapp.domain.enumeration.TipoMamifero;
/**
 * Integration tests for the {@link MamiferoResource} REST controller.
 */
@SpringBootTest(classes = Mamiferos2020App.class)

@AutoConfigureMockMvc
@WithMockUser
public class MamiferoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoMamifero DEFAULT_TIPO = TipoMamifero.GATO;
    private static final TipoMamifero UPDATED_TIPO = TipoMamifero.MACACO;

    private static final String DEFAULT_APELIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Double DEFAULT_ALTURA = 1D;
    private static final Double UPDATED_ALTURA = 2D;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Double DEFAULT_COMPRIMENTO = 1D;
    private static final Double UPDATED_COMPRIMENTO = 2D;

    @Autowired
    private MamiferoRepository mamiferoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMamiferoMockMvc;

    private Mamifero mamifero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mamifero createEntity(EntityManager em) {
        Mamifero mamifero = new Mamifero()
            .nome(DEFAULT_NOME)
            .tipo(DEFAULT_TIPO)
            .apelido(DEFAULT_APELIDO)
            .numero(DEFAULT_NUMERO)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO)
            .comprimento(DEFAULT_COMPRIMENTO);
        return mamifero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mamifero createUpdatedEntity(EntityManager em) {
        Mamifero mamifero = new Mamifero()
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .apelido(UPDATED_APELIDO)
            .numero(UPDATED_NUMERO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .comprimento(UPDATED_COMPRIMENTO);
        return mamifero;
    }

    @BeforeEach
    public void initTest() {
        mamifero = createEntity(em);
    }

    @Test
    @Transactional
    public void createMamifero() throws Exception {
        int databaseSizeBeforeCreate = mamiferoRepository.findAll().size();

        // Create the Mamifero
        restMamiferoMockMvc.perform(post("/api/mamiferos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mamifero)))
            .andExpect(status().isCreated());

        // Validate the Mamifero in the database
        List<Mamifero> mamiferoList = mamiferoRepository.findAll();
        assertThat(mamiferoList).hasSize(databaseSizeBeforeCreate + 1);
        Mamifero testMamifero = mamiferoList.get(mamiferoList.size() - 1);
        assertThat(testMamifero.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMamifero.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testMamifero.getApelido()).isEqualTo(DEFAULT_APELIDO);
        assertThat(testMamifero.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testMamifero.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testMamifero.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testMamifero.getComprimento()).isEqualTo(DEFAULT_COMPRIMENTO);
    }

    @Test
    @Transactional
    public void createMamiferoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mamiferoRepository.findAll().size();

        // Create the Mamifero with an existing ID
        mamifero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMamiferoMockMvc.perform(post("/api/mamiferos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mamifero)))
            .andExpect(status().isBadRequest());

        // Validate the Mamifero in the database
        List<Mamifero> mamiferoList = mamiferoRepository.findAll();
        assertThat(mamiferoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMamiferos() throws Exception {
        // Initialize the database
        mamiferoRepository.saveAndFlush(mamifero);

        // Get all the mamiferoList
        restMamiferoMockMvc.perform(get("/api/mamiferos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mamifero.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].apelido").value(hasItem(DEFAULT_APELIDO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].comprimento").value(hasItem(DEFAULT_COMPRIMENTO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMamifero() throws Exception {
        // Initialize the database
        mamiferoRepository.saveAndFlush(mamifero);

        // Get the mamifero
        restMamiferoMockMvc.perform(get("/api/mamiferos/{id}", mamifero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mamifero.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.apelido").value(DEFAULT_APELIDO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.comprimento").value(DEFAULT_COMPRIMENTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMamifero() throws Exception {
        // Get the mamifero
        restMamiferoMockMvc.perform(get("/api/mamiferos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMamifero() throws Exception {
        // Initialize the database
        mamiferoRepository.saveAndFlush(mamifero);

        int databaseSizeBeforeUpdate = mamiferoRepository.findAll().size();

        // Update the mamifero
        Mamifero updatedMamifero = mamiferoRepository.findById(mamifero.getId()).get();
        // Disconnect from session so that the updates on updatedMamifero are not directly saved in db
        em.detach(updatedMamifero);
        updatedMamifero
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .apelido(UPDATED_APELIDO)
            .numero(UPDATED_NUMERO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .comprimento(UPDATED_COMPRIMENTO);

        restMamiferoMockMvc.perform(put("/api/mamiferos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMamifero)))
            .andExpect(status().isOk());

        // Validate the Mamifero in the database
        List<Mamifero> mamiferoList = mamiferoRepository.findAll();
        assertThat(mamiferoList).hasSize(databaseSizeBeforeUpdate);
        Mamifero testMamifero = mamiferoList.get(mamiferoList.size() - 1);
        assertThat(testMamifero.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMamifero.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testMamifero.getApelido()).isEqualTo(UPDATED_APELIDO);
        assertThat(testMamifero.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testMamifero.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testMamifero.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testMamifero.getComprimento()).isEqualTo(UPDATED_COMPRIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingMamifero() throws Exception {
        int databaseSizeBeforeUpdate = mamiferoRepository.findAll().size();

        // Create the Mamifero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMamiferoMockMvc.perform(put("/api/mamiferos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mamifero)))
            .andExpect(status().isBadRequest());

        // Validate the Mamifero in the database
        List<Mamifero> mamiferoList = mamiferoRepository.findAll();
        assertThat(mamiferoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMamifero() throws Exception {
        // Initialize the database
        mamiferoRepository.saveAndFlush(mamifero);

        int databaseSizeBeforeDelete = mamiferoRepository.findAll().size();

        // Delete the mamifero
        restMamiferoMockMvc.perform(delete("/api/mamiferos/{id}", mamifero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mamifero> mamiferoList = mamiferoRepository.findAll();
        assertThat(mamiferoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
