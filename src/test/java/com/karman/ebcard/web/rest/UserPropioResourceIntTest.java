package com.karman.ebcard.web.rest;

import com.karman.ebcard.EbCardApp;

import com.karman.ebcard.domain.UserPropio;
import com.karman.ebcard.repository.UserPropioRepository;
import com.karman.ebcard.service.UserPropioService;
import com.karman.ebcard.service.dto.UserPropioDTO;
import com.karman.ebcard.service.mapper.UserPropioMapper;
import com.karman.ebcard.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.karman.ebcard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserPropioResource REST controller.
 *
 * @see UserPropioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbCardApp.class)
public class UserPropioResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UserPropioRepository userPropioRepository;

    @Mock
    private UserPropioRepository userPropioRepositoryMock;

    @Autowired
    private UserPropioMapper userPropioMapper;

    @Mock
    private UserPropioService userPropioServiceMock;

    @Autowired
    private UserPropioService userPropioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserPropioMockMvc;

    private UserPropio userPropio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPropioResource userPropioResource = new UserPropioResource(userPropioService);
        this.restUserPropioMockMvc = MockMvcBuilders.standaloneSetup(userPropioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPropio createEntity(EntityManager em) {
        UserPropio userPropio = new UserPropio()
            .name(DEFAULT_NAME);
        return userPropio;
    }

    @Before
    public void initTest() {
        userPropio = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPropio() throws Exception {
        int databaseSizeBeforeCreate = userPropioRepository.findAll().size();

        // Create the UserPropio
        UserPropioDTO userPropioDTO = userPropioMapper.toDto(userPropio);
        restUserPropioMockMvc.perform(post("/api/user-propios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPropioDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPropio in the database
        List<UserPropio> userPropioList = userPropioRepository.findAll();
        assertThat(userPropioList).hasSize(databaseSizeBeforeCreate + 1);
        UserPropio testUserPropio = userPropioList.get(userPropioList.size() - 1);
        assertThat(testUserPropio.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUserPropioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPropioRepository.findAll().size();

        // Create the UserPropio with an existing ID
        userPropio.setId(1L);
        UserPropioDTO userPropioDTO = userPropioMapper.toDto(userPropio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPropioMockMvc.perform(post("/api/user-propios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPropioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPropio in the database
        List<UserPropio> userPropioList = userPropioRepository.findAll();
        assertThat(userPropioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserPropios() throws Exception {
        // Initialize the database
        userPropioRepository.saveAndFlush(userPropio);

        // Get all the userPropioList
        restUserPropioMockMvc.perform(get("/api/user-propios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPropio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserPropiosWithEagerRelationshipsIsEnabled() throws Exception {
        UserPropioResource userPropioResource = new UserPropioResource(userPropioServiceMock);
        when(userPropioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserPropioMockMvc = MockMvcBuilders.standaloneSetup(userPropioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserPropioMockMvc.perform(get("/api/user-propios?eagerload=true"))
        .andExpect(status().isOk());

        verify(userPropioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserPropiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserPropioResource userPropioResource = new UserPropioResource(userPropioServiceMock);
            when(userPropioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserPropioMockMvc = MockMvcBuilders.standaloneSetup(userPropioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserPropioMockMvc.perform(get("/api/user-propios?eagerload=true"))
        .andExpect(status().isOk());

            verify(userPropioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserPropio() throws Exception {
        // Initialize the database
        userPropioRepository.saveAndFlush(userPropio);

        // Get the userPropio
        restUserPropioMockMvc.perform(get("/api/user-propios/{id}", userPropio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPropio.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPropio() throws Exception {
        // Get the userPropio
        restUserPropioMockMvc.perform(get("/api/user-propios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPropio() throws Exception {
        // Initialize the database
        userPropioRepository.saveAndFlush(userPropio);

        int databaseSizeBeforeUpdate = userPropioRepository.findAll().size();

        // Update the userPropio
        UserPropio updatedUserPropio = userPropioRepository.findById(userPropio.getId()).get();
        // Disconnect from session so that the updates on updatedUserPropio are not directly saved in db
        em.detach(updatedUserPropio);
        updatedUserPropio
            .name(UPDATED_NAME);
        UserPropioDTO userPropioDTO = userPropioMapper.toDto(updatedUserPropio);

        restUserPropioMockMvc.perform(put("/api/user-propios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPropioDTO)))
            .andExpect(status().isOk());

        // Validate the UserPropio in the database
        List<UserPropio> userPropioList = userPropioRepository.findAll();
        assertThat(userPropioList).hasSize(databaseSizeBeforeUpdate);
        UserPropio testUserPropio = userPropioList.get(userPropioList.size() - 1);
        assertThat(testUserPropio.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPropio() throws Exception {
        int databaseSizeBeforeUpdate = userPropioRepository.findAll().size();

        // Create the UserPropio
        UserPropioDTO userPropioDTO = userPropioMapper.toDto(userPropio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPropioMockMvc.perform(put("/api/user-propios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPropioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPropio in the database
        List<UserPropio> userPropioList = userPropioRepository.findAll();
        assertThat(userPropioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserPropio() throws Exception {
        // Initialize the database
        userPropioRepository.saveAndFlush(userPropio);

        int databaseSizeBeforeDelete = userPropioRepository.findAll().size();

        // Delete the userPropio
        restUserPropioMockMvc.perform(delete("/api/user-propios/{id}", userPropio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPropio> userPropioList = userPropioRepository.findAll();
        assertThat(userPropioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPropio.class);
        UserPropio userPropio1 = new UserPropio();
        userPropio1.setId(1L);
        UserPropio userPropio2 = new UserPropio();
        userPropio2.setId(userPropio1.getId());
        assertThat(userPropio1).isEqualTo(userPropio2);
        userPropio2.setId(2L);
        assertThat(userPropio1).isNotEqualTo(userPropio2);
        userPropio1.setId(null);
        assertThat(userPropio1).isNotEqualTo(userPropio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPropioDTO.class);
        UserPropioDTO userPropioDTO1 = new UserPropioDTO();
        userPropioDTO1.setId(1L);
        UserPropioDTO userPropioDTO2 = new UserPropioDTO();
        assertThat(userPropioDTO1).isNotEqualTo(userPropioDTO2);
        userPropioDTO2.setId(userPropioDTO1.getId());
        assertThat(userPropioDTO1).isEqualTo(userPropioDTO2);
        userPropioDTO2.setId(2L);
        assertThat(userPropioDTO1).isNotEqualTo(userPropioDTO2);
        userPropioDTO1.setId(null);
        assertThat(userPropioDTO1).isNotEqualTo(userPropioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userPropioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userPropioMapper.fromId(null)).isNull();
    }
}
