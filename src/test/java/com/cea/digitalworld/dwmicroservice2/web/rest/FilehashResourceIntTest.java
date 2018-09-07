package com.cea.digitalworld.dwmicroservice2.web.rest;

import com.cea.digitalworld.dwmicroservice2.Jhipsterangularjsmd5App;

import com.cea.digitalworld.dwmicroservice2.domain.Filehash;
import com.cea.digitalworld.dwmicroservice2.repository.FilehashRepository;
import com.cea.digitalworld.dwmicroservice2.service.FilehashService;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;
import com.cea.digitalworld.dwmicroservice2.service.mapper.FilehashMapper;
import com.cea.digitalworld.dwmicroservice2.web.rest.errors.ExceptionTranslator;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashCriteria;
import com.cea.digitalworld.dwmicroservice2.service.FilehashQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cea.digitalworld.dwmicroservice2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilehashResource REST controller.
 *
 * @see FilehashResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jhipsterangularjsmd5App.class)
public class FilehashResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_OF_FILE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_OF_FILE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT_OF_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_OF_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_OF_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private FilehashRepository filehashRepository;


    @Autowired
    private FilehashMapper filehashMapper;
    

    @Autowired
    private FilehashService filehashService;

    @Autowired
    private FilehashQueryService filehashQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilehashMockMvc;

    private Filehash filehash;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilehashResource filehashResource = new FilehashResource(filehashService, filehashQueryService);
        this.restFilehashMockMvc = MockMvcBuilders.standaloneSetup(filehashResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filehash createEntity(EntityManager em) {
        Filehash filehash = new Filehash()
            .name(DEFAULT_NAME)
            .hashOfFile(DEFAULT_HASH_OF_FILE)
            .contentOfFile(DEFAULT_CONTENT_OF_FILE)
            .contentOfFileContentType(DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE);
        return filehash;
    }

    @Before
    public void initTest() {
        filehash = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilehash() throws Exception {
        int databaseSizeBeforeCreate = filehashRepository.findAll().size();

        // Create the Filehash
        FilehashDTO filehashDTO = filehashMapper.toDto(filehash);
        restFilehashMockMvc.perform(post("/api/filehashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filehashDTO)))
            .andExpect(status().isCreated());

        // Validate the Filehash in the database
        List<Filehash> filehashList = filehashRepository.findAll();
        assertThat(filehashList).hasSize(databaseSizeBeforeCreate + 1);
        Filehash testFilehash = filehashList.get(filehashList.size() - 1);
        assertThat(testFilehash.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFilehash.getHashOfFile()).isEqualTo(DEFAULT_HASH_OF_FILE);
        assertThat(testFilehash.getContentOfFile()).isEqualTo(DEFAULT_CONTENT_OF_FILE);
        assertThat(testFilehash.getContentOfFileContentType()).isEqualTo(DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFilehashWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filehashRepository.findAll().size();

        // Create the Filehash with an existing ID
        filehash.setId(1L);
        FilehashDTO filehashDTO = filehashMapper.toDto(filehash);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilehashMockMvc.perform(post("/api/filehashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filehashDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filehash in the database
        List<Filehash> filehashList = filehashRepository.findAll();
        assertThat(filehashList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilehashes() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList
        restFilehashMockMvc.perform(get("/api/filehashes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filehash.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hashOfFile").value(hasItem(DEFAULT_HASH_OF_FILE.toString())))
            .andExpect(jsonPath("$.[*].contentOfFileContentType").value(hasItem(DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentOfFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_OF_FILE))));
    }
    

    @Test
    @Transactional
    public void getFilehash() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get the filehash
        restFilehashMockMvc.perform(get("/api/filehashes/{id}", filehash.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filehash.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.hashOfFile").value(DEFAULT_HASH_OF_FILE.toString()))
            .andExpect(jsonPath("$.contentOfFileContentType").value(DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentOfFile").value(Base64Utils.encodeToString(DEFAULT_CONTENT_OF_FILE)));
    }

    @Test
    @Transactional
    public void getAllFilehashesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where name equals to DEFAULT_NAME
        defaultFilehashShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the filehashList where name equals to UPDATED_NAME
        defaultFilehashShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilehashesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFilehashShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the filehashList where name equals to UPDATED_NAME
        defaultFilehashShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFilehashesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where name is not null
        defaultFilehashShouldBeFound("name.specified=true");

        // Get all the filehashList where name is null
        defaultFilehashShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilehashesByHashOfFileIsEqualToSomething() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where hashOfFile equals to DEFAULT_HASH_OF_FILE
        defaultFilehashShouldBeFound("hashOfFile.equals=" + DEFAULT_HASH_OF_FILE);

        // Get all the filehashList where hashOfFile equals to UPDATED_HASH_OF_FILE
        defaultFilehashShouldNotBeFound("hashOfFile.equals=" + UPDATED_HASH_OF_FILE);
    }

    @Test
    @Transactional
    public void getAllFilehashesByHashOfFileIsInShouldWork() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where hashOfFile in DEFAULT_HASH_OF_FILE or UPDATED_HASH_OF_FILE
        defaultFilehashShouldBeFound("hashOfFile.in=" + DEFAULT_HASH_OF_FILE + "," + UPDATED_HASH_OF_FILE);

        // Get all the filehashList where hashOfFile equals to UPDATED_HASH_OF_FILE
        defaultFilehashShouldNotBeFound("hashOfFile.in=" + UPDATED_HASH_OF_FILE);
    }

    @Test
    @Transactional
    public void getAllFilehashesByHashOfFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        // Get all the filehashList where hashOfFile is not null
        defaultFilehashShouldBeFound("hashOfFile.specified=true");

        // Get all the filehashList where hashOfFile is null
        defaultFilehashShouldNotBeFound("hashOfFile.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFilehashShouldBeFound(String filter) throws Exception {
        restFilehashMockMvc.perform(get("/api/filehashes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filehash.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hashOfFile").value(hasItem(DEFAULT_HASH_OF_FILE.toString())))
            .andExpect(jsonPath("$.[*].contentOfFileContentType").value(hasItem(DEFAULT_CONTENT_OF_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentOfFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_OF_FILE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFilehashShouldNotBeFound(String filter) throws Exception {
        restFilehashMockMvc.perform(get("/api/filehashes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingFilehash() throws Exception {
        // Get the filehash
        restFilehashMockMvc.perform(get("/api/filehashes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilehash() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        int databaseSizeBeforeUpdate = filehashRepository.findAll().size();

        // Update the filehash
        Filehash updatedFilehash = filehashRepository.findById(filehash.getId()).get();
        // Disconnect from session so that the updates on updatedFilehash are not directly saved in db
        em.detach(updatedFilehash);
        updatedFilehash
            .name(UPDATED_NAME)
            .hashOfFile(UPDATED_HASH_OF_FILE)
            .contentOfFile(UPDATED_CONTENT_OF_FILE)
            .contentOfFileContentType(UPDATED_CONTENT_OF_FILE_CONTENT_TYPE);
        FilehashDTO filehashDTO = filehashMapper.toDto(updatedFilehash);

        restFilehashMockMvc.perform(put("/api/filehashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filehashDTO)))
            .andExpect(status().isOk());

        // Validate the Filehash in the database
        List<Filehash> filehashList = filehashRepository.findAll();
        assertThat(filehashList).hasSize(databaseSizeBeforeUpdate);
        Filehash testFilehash = filehashList.get(filehashList.size() - 1);
        assertThat(testFilehash.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFilehash.getHashOfFile()).isEqualTo(UPDATED_HASH_OF_FILE);
        assertThat(testFilehash.getContentOfFile()).isEqualTo(UPDATED_CONTENT_OF_FILE);
        assertThat(testFilehash.getContentOfFileContentType()).isEqualTo(UPDATED_CONTENT_OF_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilehash() throws Exception {
        int databaseSizeBeforeUpdate = filehashRepository.findAll().size();

        // Create the Filehash
        FilehashDTO filehashDTO = filehashMapper.toDto(filehash);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilehashMockMvc.perform(put("/api/filehashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filehashDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filehash in the database
        List<Filehash> filehashList = filehashRepository.findAll();
        assertThat(filehashList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFilehash() throws Exception {
        // Initialize the database
        filehashRepository.saveAndFlush(filehash);

        int databaseSizeBeforeDelete = filehashRepository.findAll().size();

        // Get the filehash
        restFilehashMockMvc.perform(delete("/api/filehashes/{id}", filehash.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filehash> filehashList = filehashRepository.findAll();
        assertThat(filehashList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filehash.class);
        Filehash filehash1 = new Filehash();
        filehash1.setId(1L);
        Filehash filehash2 = new Filehash();
        filehash2.setId(filehash1.getId());
        assertThat(filehash1).isEqualTo(filehash2);
        filehash2.setId(2L);
        assertThat(filehash1).isNotEqualTo(filehash2);
        filehash1.setId(null);
        assertThat(filehash1).isNotEqualTo(filehash2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilehashDTO.class);
        FilehashDTO filehashDTO1 = new FilehashDTO();
        filehashDTO1.setId(1L);
        FilehashDTO filehashDTO2 = new FilehashDTO();
        assertThat(filehashDTO1).isNotEqualTo(filehashDTO2);
        filehashDTO2.setId(filehashDTO1.getId());
        assertThat(filehashDTO1).isEqualTo(filehashDTO2);
        filehashDTO2.setId(2L);
        assertThat(filehashDTO1).isNotEqualTo(filehashDTO2);
        filehashDTO1.setId(null);
        assertThat(filehashDTO1).isNotEqualTo(filehashDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(filehashMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(filehashMapper.fromId(null)).isNull();
    }
}
