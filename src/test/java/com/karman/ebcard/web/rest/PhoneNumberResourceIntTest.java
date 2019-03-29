package com.karman.ebcard.web.rest;

import com.karman.ebcard.EbCardApp;

import com.karman.ebcard.domain.PhoneNumber;
import com.karman.ebcard.repository.PhoneNumberRepository;
import com.karman.ebcard.service.PhoneNumberService;
import com.karman.ebcard.service.dto.PhoneNumberDTO;
import com.karman.ebcard.service.mapper.PhoneNumberMapper;
import com.karman.ebcard.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.karman.ebcard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karman.ebcard.domain.enumeration.NetworkOperator;
/**
 * Test class for the PhoneNumberResource REST controller.
 *
 * @see PhoneNumberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbCardApp.class)
public class PhoneNumberResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final NetworkOperator DEFAULT_NETWORK_OPERATOR = NetworkOperator.MOVISTAR;
    private static final NetworkOperator UPDATED_NETWORK_OPERATOR = NetworkOperator.CLARO;

    private static final Boolean DEFAULT_HAS_WHATSAPP = false;
    private static final Boolean UPDATED_HAS_WHATSAPP = true;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private PhoneNumberMapper phoneNumberMapper;

    @Autowired
    private PhoneNumberService phoneNumberService;

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

    private MockMvc restPhoneNumberMockMvc;

    private PhoneNumber phoneNumber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhoneNumberResource phoneNumberResource = new PhoneNumberResource(phoneNumberService);
        this.restPhoneNumberMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberResource)
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
    public static PhoneNumber createEntity(EntityManager em) {
        PhoneNumber phoneNumber = new PhoneNumber()
            .number(DEFAULT_NUMBER)
            .networkOperator(DEFAULT_NETWORK_OPERATOR)
            .hasWhatsapp(DEFAULT_HAS_WHATSAPP);
        return phoneNumber;
    }

    @Before
    public void initTest() {
        phoneNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber
        PhoneNumberDTO phoneNumberDTO = phoneNumberMapper.toDto(phoneNumber);
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberDTO)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPhoneNumber.getNetworkOperator()).isEqualTo(DEFAULT_NETWORK_OPERATOR);
        assertThat(testPhoneNumber.isHasWhatsapp()).isEqualTo(DEFAULT_HAS_WHATSAPP);
    }

    @Test
    @Transactional
    public void createPhoneNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber with an existing ID
        phoneNumber.setId(1L);
        PhoneNumberDTO phoneNumberDTO = phoneNumberMapper.toDto(phoneNumber);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get all the phoneNumberList
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].networkOperator").value(hasItem(DEFAULT_NETWORK_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].hasWhatsapp").value(hasItem(DEFAULT_HAS_WHATSAPP.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", phoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.networkOperator").value(DEFAULT_NETWORK_OPERATOR.toString()))
            .andExpect(jsonPath("$.hasWhatsapp").value(DEFAULT_HAS_WHATSAPP.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Update the phoneNumber
        PhoneNumber updatedPhoneNumber = phoneNumberRepository.findById(phoneNumber.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneNumber are not directly saved in db
        em.detach(updatedPhoneNumber);
        updatedPhoneNumber
            .number(UPDATED_NUMBER)
            .networkOperator(UPDATED_NETWORK_OPERATOR)
            .hasWhatsapp(UPDATED_HAS_WHATSAPP);
        PhoneNumberDTO phoneNumberDTO = phoneNumberMapper.toDto(updatedPhoneNumber);

        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberDTO)))
            .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPhoneNumber.getNetworkOperator()).isEqualTo(UPDATED_NETWORK_OPERATOR);
        assertThat(testPhoneNumber.isHasWhatsapp()).isEqualTo(UPDATED_HAS_WHATSAPP);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumber() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber
        PhoneNumberDTO phoneNumberDTO = phoneNumberMapper.toDto(phoneNumber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        int databaseSizeBeforeDelete = phoneNumberRepository.findAll().size();

        // Delete the phoneNumber
        restPhoneNumberMockMvc.perform(delete("/api/phone-numbers/{id}", phoneNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumber.class);
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(1L);
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(phoneNumber1.getId());
        assertThat(phoneNumber1).isEqualTo(phoneNumber2);
        phoneNumber2.setId(2L);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
        phoneNumber1.setId(null);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberDTO.class);
        PhoneNumberDTO phoneNumberDTO1 = new PhoneNumberDTO();
        phoneNumberDTO1.setId(1L);
        PhoneNumberDTO phoneNumberDTO2 = new PhoneNumberDTO();
        assertThat(phoneNumberDTO1).isNotEqualTo(phoneNumberDTO2);
        phoneNumberDTO2.setId(phoneNumberDTO1.getId());
        assertThat(phoneNumberDTO1).isEqualTo(phoneNumberDTO2);
        phoneNumberDTO2.setId(2L);
        assertThat(phoneNumberDTO1).isNotEqualTo(phoneNumberDTO2);
        phoneNumberDTO1.setId(null);
        assertThat(phoneNumberDTO1).isNotEqualTo(phoneNumberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(phoneNumberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(phoneNumberMapper.fromId(null)).isNull();
    }
}
