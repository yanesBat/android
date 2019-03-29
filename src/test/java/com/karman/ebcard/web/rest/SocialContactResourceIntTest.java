package com.karman.ebcard.web.rest;

import com.karman.ebcard.EbCardApp;

import com.karman.ebcard.domain.SocialContact;
import com.karman.ebcard.repository.SocialContactRepository;
import com.karman.ebcard.service.SocialContactService;
import com.karman.ebcard.service.dto.SocialContactDTO;
import com.karman.ebcard.service.mapper.SocialContactMapper;
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

import com.karman.ebcard.domain.enumeration.SocialNetwork;
/**
 * Test class for the SocialContactResource REST controller.
 *
 * @see SocialContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbCardApp.class)
public class SocialContactResourceIntTest {

    private static final SocialNetwork DEFAULT_SOCIAL_NETWORK = SocialNetwork.FACEBOOK;
    private static final SocialNetwork UPDATED_SOCIAL_NETWORK = SocialNetwork.TWITTER;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    @Autowired
    private SocialContactRepository socialContactRepository;

    @Autowired
    private SocialContactMapper socialContactMapper;

    @Autowired
    private SocialContactService socialContactService;

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

    private MockMvc restSocialContactMockMvc;

    private SocialContact socialContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocialContactResource socialContactResource = new SocialContactResource(socialContactService);
        this.restSocialContactMockMvc = MockMvcBuilders.standaloneSetup(socialContactResource)
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
    public static SocialContact createEntity(EntityManager em) {
        SocialContact socialContact = new SocialContact()
            .socialNetwork(DEFAULT_SOCIAL_NETWORK)
            .link(DEFAULT_LINK);
        return socialContact;
    }

    @Before
    public void initTest() {
        socialContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialContact() throws Exception {
        int databaseSizeBeforeCreate = socialContactRepository.findAll().size();

        // Create the SocialContact
        SocialContactDTO socialContactDTO = socialContactMapper.toDto(socialContact);
        restSocialContactMockMvc.perform(post("/api/social-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialContactDTO)))
            .andExpect(status().isCreated());

        // Validate the SocialContact in the database
        List<SocialContact> socialContactList = socialContactRepository.findAll();
        assertThat(socialContactList).hasSize(databaseSizeBeforeCreate + 1);
        SocialContact testSocialContact = socialContactList.get(socialContactList.size() - 1);
        assertThat(testSocialContact.getSocialNetwork()).isEqualTo(DEFAULT_SOCIAL_NETWORK);
        assertThat(testSocialContact.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void createSocialContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialContactRepository.findAll().size();

        // Create the SocialContact with an existing ID
        socialContact.setId(1L);
        SocialContactDTO socialContactDTO = socialContactMapper.toDto(socialContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialContactMockMvc.perform(post("/api/social-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SocialContact in the database
        List<SocialContact> socialContactList = socialContactRepository.findAll();
        assertThat(socialContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialContacts() throws Exception {
        // Initialize the database
        socialContactRepository.saveAndFlush(socialContact);

        // Get all the socialContactList
        restSocialContactMockMvc.perform(get("/api/social-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].socialNetwork").value(hasItem(DEFAULT_SOCIAL_NETWORK.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())));
    }
    
    @Test
    @Transactional
    public void getSocialContact() throws Exception {
        // Initialize the database
        socialContactRepository.saveAndFlush(socialContact);

        // Get the socialContact
        restSocialContactMockMvc.perform(get("/api/social-contacts/{id}", socialContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialContact.getId().intValue()))
            .andExpect(jsonPath("$.socialNetwork").value(DEFAULT_SOCIAL_NETWORK.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialContact() throws Exception {
        // Get the socialContact
        restSocialContactMockMvc.perform(get("/api/social-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialContact() throws Exception {
        // Initialize the database
        socialContactRepository.saveAndFlush(socialContact);

        int databaseSizeBeforeUpdate = socialContactRepository.findAll().size();

        // Update the socialContact
        SocialContact updatedSocialContact = socialContactRepository.findById(socialContact.getId()).get();
        // Disconnect from session so that the updates on updatedSocialContact are not directly saved in db
        em.detach(updatedSocialContact);
        updatedSocialContact
            .socialNetwork(UPDATED_SOCIAL_NETWORK)
            .link(UPDATED_LINK);
        SocialContactDTO socialContactDTO = socialContactMapper.toDto(updatedSocialContact);

        restSocialContactMockMvc.perform(put("/api/social-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialContactDTO)))
            .andExpect(status().isOk());

        // Validate the SocialContact in the database
        List<SocialContact> socialContactList = socialContactRepository.findAll();
        assertThat(socialContactList).hasSize(databaseSizeBeforeUpdate);
        SocialContact testSocialContact = socialContactList.get(socialContactList.size() - 1);
        assertThat(testSocialContact.getSocialNetwork()).isEqualTo(UPDATED_SOCIAL_NETWORK);
        assertThat(testSocialContact.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialContact() throws Exception {
        int databaseSizeBeforeUpdate = socialContactRepository.findAll().size();

        // Create the SocialContact
        SocialContactDTO socialContactDTO = socialContactMapper.toDto(socialContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialContactMockMvc.perform(put("/api/social-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SocialContact in the database
        List<SocialContact> socialContactList = socialContactRepository.findAll();
        assertThat(socialContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSocialContact() throws Exception {
        // Initialize the database
        socialContactRepository.saveAndFlush(socialContact);

        int databaseSizeBeforeDelete = socialContactRepository.findAll().size();

        // Delete the socialContact
        restSocialContactMockMvc.perform(delete("/api/social-contacts/{id}", socialContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialContact> socialContactList = socialContactRepository.findAll();
        assertThat(socialContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialContact.class);
        SocialContact socialContact1 = new SocialContact();
        socialContact1.setId(1L);
        SocialContact socialContact2 = new SocialContact();
        socialContact2.setId(socialContact1.getId());
        assertThat(socialContact1).isEqualTo(socialContact2);
        socialContact2.setId(2L);
        assertThat(socialContact1).isNotEqualTo(socialContact2);
        socialContact1.setId(null);
        assertThat(socialContact1).isNotEqualTo(socialContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialContactDTO.class);
        SocialContactDTO socialContactDTO1 = new SocialContactDTO();
        socialContactDTO1.setId(1L);
        SocialContactDTO socialContactDTO2 = new SocialContactDTO();
        assertThat(socialContactDTO1).isNotEqualTo(socialContactDTO2);
        socialContactDTO2.setId(socialContactDTO1.getId());
        assertThat(socialContactDTO1).isEqualTo(socialContactDTO2);
        socialContactDTO2.setId(2L);
        assertThat(socialContactDTO1).isNotEqualTo(socialContactDTO2);
        socialContactDTO1.setId(null);
        assertThat(socialContactDTO1).isNotEqualTo(socialContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(socialContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(socialContactMapper.fromId(null)).isNull();
    }
}
