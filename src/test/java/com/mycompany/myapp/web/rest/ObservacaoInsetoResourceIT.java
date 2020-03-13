package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Mamiferos2020App;
import com.mycompany.myapp.domain.ObservacaoInseto;
import com.mycompany.myapp.repository.ObservacaoInsetoRepository;

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
 * Integration tests for the {@link ObservacaoInsetoResource} REST controller.
 */
@SpringBootTest(classes = Mamiferos2020App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ObservacaoInsetoResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    @Autowired
    private ObservacaoInsetoRepository observacaoInsetoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservacaoInsetoMockMvc;

    private ObservacaoInseto observacaoInseto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoInseto createEntity(EntityManager em) {
        ObservacaoInseto observacaoInseto = new ObservacaoInseto()
            .data(DEFAULT_DATA)
            .local(DEFAULT_LOCAL)
            .quantidade(DEFAULT_QUANTIDADE);
        return observacaoInseto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoInseto createUpdatedEntity(EntityManager em) {
        ObservacaoInseto observacaoInseto = new ObservacaoInseto()
            .data(UPDATED_DATA)
            .local(UPDATED_LOCAL)
            .quantidade(UPDATED_QUANTIDADE);
        return observacaoInseto;
    }

    @BeforeEach
    public void initTest() {
        observacaoInseto = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservacaoInseto() throws Exception {
        int databaseSizeBeforeCreate = observacaoInsetoRepository.findAll().size();

        // Create the ObservacaoInseto
        restObservacaoInsetoMockMvc.perform(post("/api/observacao-insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoInseto)))
            .andExpect(status().isCreated());

        // Validate the ObservacaoInseto in the database
        List<ObservacaoInseto> observacaoInsetoList = observacaoInsetoRepository.findAll();
        assertThat(observacaoInsetoList).hasSize(databaseSizeBeforeCreate + 1);
        ObservacaoInseto testObservacaoInseto = observacaoInsetoList.get(observacaoInsetoList.size() - 1);
        assertThat(testObservacaoInseto.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testObservacaoInseto.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testObservacaoInseto.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
    }

    @Test
    @Transactional
    public void createObservacaoInsetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observacaoInsetoRepository.findAll().size();

        // Create the ObservacaoInseto with an existing ID
        observacaoInseto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservacaoInsetoMockMvc.perform(post("/api/observacao-insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoInseto)))
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoInseto in the database
        List<ObservacaoInseto> observacaoInsetoList = observacaoInsetoRepository.findAll();
        assertThat(observacaoInsetoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllObservacaoInsetos() throws Exception {
        // Initialize the database
        observacaoInsetoRepository.saveAndFlush(observacaoInseto);

        // Get all the observacaoInsetoList
        restObservacaoInsetoMockMvc.perform(get("/api/observacao-insetos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observacaoInseto.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }
    
    @Test
    @Transactional
    public void getObservacaoInseto() throws Exception {
        // Initialize the database
        observacaoInsetoRepository.saveAndFlush(observacaoInseto);

        // Get the observacaoInseto
        restObservacaoInsetoMockMvc.perform(get("/api/observacao-insetos/{id}", observacaoInseto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observacaoInseto.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingObservacaoInseto() throws Exception {
        // Get the observacaoInseto
        restObservacaoInsetoMockMvc.perform(get("/api/observacao-insetos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservacaoInseto() throws Exception {
        // Initialize the database
        observacaoInsetoRepository.saveAndFlush(observacaoInseto);

        int databaseSizeBeforeUpdate = observacaoInsetoRepository.findAll().size();

        // Update the observacaoInseto
        ObservacaoInseto updatedObservacaoInseto = observacaoInsetoRepository.findById(observacaoInseto.getId()).get();
        // Disconnect from session so that the updates on updatedObservacaoInseto are not directly saved in db
        em.detach(updatedObservacaoInseto);
        updatedObservacaoInseto
            .data(UPDATED_DATA)
            .local(UPDATED_LOCAL)
            .quantidade(UPDATED_QUANTIDADE);

        restObservacaoInsetoMockMvc.perform(put("/api/observacao-insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObservacaoInseto)))
            .andExpect(status().isOk());

        // Validate the ObservacaoInseto in the database
        List<ObservacaoInseto> observacaoInsetoList = observacaoInsetoRepository.findAll();
        assertThat(observacaoInsetoList).hasSize(databaseSizeBeforeUpdate);
        ObservacaoInseto testObservacaoInseto = observacaoInsetoList.get(observacaoInsetoList.size() - 1);
        assertThat(testObservacaoInseto.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testObservacaoInseto.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testObservacaoInseto.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservacaoInseto() throws Exception {
        int databaseSizeBeforeUpdate = observacaoInsetoRepository.findAll().size();

        // Create the ObservacaoInseto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservacaoInsetoMockMvc.perform(put("/api/observacao-insetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observacaoInseto)))
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoInseto in the database
        List<ObservacaoInseto> observacaoInsetoList = observacaoInsetoRepository.findAll();
        assertThat(observacaoInsetoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservacaoInseto() throws Exception {
        // Initialize the database
        observacaoInsetoRepository.saveAndFlush(observacaoInseto);

        int databaseSizeBeforeDelete = observacaoInsetoRepository.findAll().size();

        // Delete the observacaoInseto
        restObservacaoInsetoMockMvc.perform(delete("/api/observacao-insetos/{id}", observacaoInseto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObservacaoInseto> observacaoInsetoList = observacaoInsetoRepository.findAll();
        assertThat(observacaoInsetoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
