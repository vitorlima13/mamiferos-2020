package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Mamiferos2020App;
import com.mycompany.myapp.domain.Inseto;
import com.mycompany.myapp.repository.InsetoRepository;

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

import com.mycompany.myapp.domain.enumeration.TipoInseto;
/**
 * Integration tests for the {@link InsetoResource} REST controller.
 */
@SpringBootTest(classes = Mamiferos2020App.class)

@AutoConfigureMockMvc
@WithMockUser
public class InsetoResourceIT {

    private static final TipoInseto DEFAULT_TIPO = TipoInseto.BARATA;
    private static final TipoInseto UPDATED_TIPO = TipoInseto.BESOURO;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Double DEFAULT_ALTURA = 1D;
    private static final Double UPDATED_ALTURA = 2D;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Double DEFAULT_COMPRIMENTO = 1D;
    private static final Double UPDATED_COMPRIMENTO = 2D;

    @Autowired
    private InsetoRepository insetoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsetoMockMvc;

    private Inseto inseto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inseto createEntity(EntityManager em) {
        Inseto inseto = new Inseto()
            .tipo(DEFAULT_TIPO)
            .numero(DEFAULT_NUMERO)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO)
            .comprimento(DEFAULT_COMPRIMENTO);
        return inseto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inseto createUpdatedEntity(EntityManager em) {
        Inseto inseto = new Inseto()
            .tipo(UPDATED_TIPO)
            .numero(UPDATED_NUMERO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .comprimento(UPDATED_COMPRIMENTO);
        return inseto;
    }

    @BeforeEach
    public void initTest() {
        inseto = createEntity(em);
    }

    @Test
    @Transactional
    public void createInseto() throws Exception {
        int databaseSizeBeforeCreate = insetoRepository.findAll().size();

        // Create the Inseto
        restInsetoMockMvc.perform(post("/api/insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inseto)))
            .andExpect(status().isCreated());

        // Validate the Inseto in the database
        List<Inseto> insetoList = insetoRepository.findAll();
        assertThat(insetoList).hasSize(databaseSizeBeforeCreate + 1);
        Inseto testInseto = insetoList.get(insetoList.size() - 1);
        assertThat(testInseto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testInseto.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInseto.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testInseto.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testInseto.getComprimento()).isEqualTo(DEFAULT_COMPRIMENTO);
    }

    @Test
    @Transactional
    public void createInsetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insetoRepository.findAll().size();

        // Create the Inseto with an existing ID
        inseto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsetoMockMvc.perform(post("/api/insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inseto)))
            .andExpect(status().isBadRequest());

        // Validate the Inseto in the database
        List<Inseto> insetoList = insetoRepository.findAll();
        assertThat(insetoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInsetos() throws Exception {
        // Initialize the database
        insetoRepository.saveAndFlush(inseto);

        // Get all the insetoList
        restInsetoMockMvc.perform(get("/api/insetos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inseto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].comprimento").value(hasItem(DEFAULT_COMPRIMENTO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getInseto() throws Exception {
        // Initialize the database
        insetoRepository.saveAndFlush(inseto);

        // Get the inseto
        restInsetoMockMvc.perform(get("/api/insetos/{id}", inseto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inseto.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.comprimento").value(DEFAULT_COMPRIMENTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInseto() throws Exception {
        // Get the inseto
        restInsetoMockMvc.perform(get("/api/insetos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInseto() throws Exception {
        // Initialize the database
        insetoRepository.saveAndFlush(inseto);

        int databaseSizeBeforeUpdate = insetoRepository.findAll().size();

        // Update the inseto
        Inseto updatedInseto = insetoRepository.findById(inseto.getId()).get();
        // Disconnect from session so that the updates on updatedInseto are not directly saved in db
        em.detach(updatedInseto);
        updatedInseto
            .tipo(UPDATED_TIPO)
            .numero(UPDATED_NUMERO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .comprimento(UPDATED_COMPRIMENTO);

        restInsetoMockMvc.perform(put("/api/insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInseto)))
            .andExpect(status().isOk());

        // Validate the Inseto in the database
        List<Inseto> insetoList = insetoRepository.findAll();
        assertThat(insetoList).hasSize(databaseSizeBeforeUpdate);
        Inseto testInseto = insetoList.get(insetoList.size() - 1);
        assertThat(testInseto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testInseto.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInseto.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testInseto.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testInseto.getComprimento()).isEqualTo(UPDATED_COMPRIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingInseto() throws Exception {
        int databaseSizeBeforeUpdate = insetoRepository.findAll().size();

        // Create the Inseto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsetoMockMvc.perform(put("/api/insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inseto)))
            .andExpect(status().isBadRequest());

        // Validate the Inseto in the database
        List<Inseto> insetoList = insetoRepository.findAll();
        assertThat(insetoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInseto() throws Exception {
        // Initialize the database
        insetoRepository.saveAndFlush(inseto);

        int databaseSizeBeforeDelete = insetoRepository.findAll().size();

        // Delete the inseto
        restInsetoMockMvc.perform(delete("/api/insetos/{id}", inseto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inseto> insetoList = insetoRepository.findAll();
        assertThat(insetoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
