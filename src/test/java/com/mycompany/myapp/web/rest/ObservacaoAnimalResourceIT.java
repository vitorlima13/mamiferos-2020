package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Mamiferos2020App;
import com.mycompany.myapp.domain.ObservacaoAnimal;
import com.mycompany.myapp.repository.ObservacaoAnimalRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ObservacaoAnimalResource} REST controller.
 */
@SpringBootTest(classes = Mamiferos2020App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ObservacaoAnimalResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Double DEFAULT_COMPRIMENTO = 1D;
    private static final Double UPDATED_COMPRIMENTO = 2D;

    private static final Double DEFAULT_ALTURA = 1D;
    private static final Double UPDATED_ALTURA = 2D;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    @Autowired
    private ObservacaoAnimalRepository observacaoAnimalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservacaoAnimalMockMvc;

    private ObservacaoAnimal observacaoAnimal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoAnimal createEntity(EntityManager em) {
        ObservacaoAnimal observacaoAnimal = new ObservacaoAnimal()
            .data(DEFAULT_DATA)
            .local(DEFAULT_LOCAL)
            .comprimento(DEFAULT_COMPRIMENTO)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO);
        return observacaoAnimal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoAnimal createUpdatedEntity(EntityManager em) {
        ObservacaoAnimal observacaoAnimal = new ObservacaoAnimal()
            .data(UPDATED_DATA)
            .local(UPDATED_LOCAL)
            .comprimento(UPDATED_COMPRIMENTO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO);
        return observacaoAnimal;
    }

    @BeforeEach
    public void initTest() {
        observacaoAnimal = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservacaoAnimal() throws Exception {
        int databaseSizeBeforeCreate = observacaoAnimalRepository.findAll().size();

        // Create the ObservacaoAnimal
        restObservacaoAnimalMockMvc.perform(post("/api/observacao-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoAnimal)))
            .andExpect(status().isCreated());

        // Validate the ObservacaoAnimal in the database
        List<ObservacaoAnimal> observacaoAnimalList = observacaoAnimalRepository.findAll();
        assertThat(observacaoAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        ObservacaoAnimal testObservacaoAnimal = observacaoAnimalList.get(observacaoAnimalList.size() - 1);
        assertThat(testObservacaoAnimal.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testObservacaoAnimal.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testObservacaoAnimal.getComprimento()).isEqualTo(DEFAULT_COMPRIMENTO);
        assertThat(testObservacaoAnimal.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testObservacaoAnimal.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    public void createObservacaoAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observacaoAnimalRepository.findAll().size();

        // Create the ObservacaoAnimal with an existing ID
        observacaoAnimal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservacaoAnimalMockMvc.perform(post("/api/observacao-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoAnimal in the database
        List<ObservacaoAnimal> observacaoAnimalList = observacaoAnimalRepository.findAll();
        assertThat(observacaoAnimalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllObservacaoAnimals() throws Exception {
        // Initialize the database
        observacaoAnimalRepository.saveAndFlush(observacaoAnimal);

        // Get all the observacaoAnimalList
        restObservacaoAnimalMockMvc.perform(get("/api/observacao-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observacaoAnimal.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].comprimento").value(hasItem(DEFAULT_COMPRIMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getObservacaoAnimal() throws Exception {
        // Initialize the database
        observacaoAnimalRepository.saveAndFlush(observacaoAnimal);

        // Get the observacaoAnimal
        restObservacaoAnimalMockMvc.perform(get("/api/observacao-animals/{id}", observacaoAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observacaoAnimal.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.comprimento").value(DEFAULT_COMPRIMENTO.doubleValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingObservacaoAnimal() throws Exception {
        // Get the observacaoAnimal
        restObservacaoAnimalMockMvc.perform(get("/api/observacao-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservacaoAnimal() throws Exception {
        // Initialize the database
        observacaoAnimalRepository.saveAndFlush(observacaoAnimal);

        int databaseSizeBeforeUpdate = observacaoAnimalRepository.findAll().size();

        // Update the observacaoAnimal
        ObservacaoAnimal updatedObservacaoAnimal = observacaoAnimalRepository.findById(observacaoAnimal.getId()).get();
        // Disconnect from session so that the updates on updatedObservacaoAnimal are not directly saved in db
        em.detach(updatedObservacaoAnimal);
        updatedObservacaoAnimal
            .data(UPDATED_DATA)
            .local(UPDATED_LOCAL)
            .comprimento(UPDATED_COMPRIMENTO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO);

        restObservacaoAnimalMockMvc.perform(put("/api/observacao-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObservacaoAnimal)))
            .andExpect(status().isOk());

        // Validate the ObservacaoAnimal in the database
        List<ObservacaoAnimal> observacaoAnimalList = observacaoAnimalRepository.findAll();
        assertThat(observacaoAnimalList).hasSize(databaseSizeBeforeUpdate);
        ObservacaoAnimal testObservacaoAnimal = observacaoAnimalList.get(observacaoAnimalList.size() - 1);
        assertThat(testObservacaoAnimal.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testObservacaoAnimal.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testObservacaoAnimal.getComprimento()).isEqualTo(UPDATED_COMPRIMENTO);
        assertThat(testObservacaoAnimal.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testObservacaoAnimal.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    public void updateNonExistingObservacaoAnimal() throws Exception {
        int databaseSizeBeforeUpdate = observacaoAnimalRepository.findAll().size();

        // Create the ObservacaoAnimal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservacaoAnimalMockMvc.perform(put("/api/observacao-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoAnimal in the database
        List<ObservacaoAnimal> observacaoAnimalList = observacaoAnimalRepository.findAll();
        assertThat(observacaoAnimalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservacaoAnimal() throws Exception {
        // Initialize the database
        observacaoAnimalRepository.saveAndFlush(observacaoAnimal);

        int databaseSizeBeforeDelete = observacaoAnimalRepository.findAll().size();

        // Delete the observacaoAnimal
        restObservacaoAnimalMockMvc.perform(delete("/api/observacao-animals/{id}", observacaoAnimal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObservacaoAnimal> observacaoAnimalList = observacaoAnimalRepository.findAll();
        assertThat(observacaoAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
