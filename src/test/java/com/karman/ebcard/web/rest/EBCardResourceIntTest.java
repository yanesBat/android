package com.karman.ebcard.web.rest;

import com.karman.ebcard.EbCardApp;

import com.karman.ebcard.domain.EBCard;
import com.karman.ebcard.repository.EBCardRepository;
import com.karman.ebcard.service.EBCardService;
import com.karman.ebcard.service.dto.EBCardDTO;
import com.karman.ebcard.service.mapper.EBCardMapper;
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

import com.karman.ebcard.domain.enumeration.PaidPlan;
/**
 * Test class for the EBCardResource REST controller.
 *
 * @see EBCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbCardApp.class)
public class EBCardResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSSINES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSSINES_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final PaidPlan DEFAULT_PAID_PLAN = PaidPlan.BRONCE;
    private static final PaidPlan UPDATED_PAID_PLAN = PaidPlan.SILVER;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private EBCardRepository eBCardRepository;

    @Autowired
    private EBCardMapper eBCardMapper;

    @Autowired
    private EBCardService eBCardService;

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

    private MockMvc restEBCardMockMvc;

    private EBCard eBCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EBCardResource eBCardResource = new EBCardResource(eBCardService);
        this.restEBCardMockMvc = MockMvcBuilders.standaloneSetup(eBCardResource)
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
    public static EBCard createEntity(EntityManager em) {
        EBCard eBCard = new EBCard()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .title(DEFAULT_TITLE)
            .subtitle(DEFAULT_SUBTITLE)
            .bussinesName(DEFAULT_BUSSINES_NAME)
            .occupation(DEFAULT_OCCUPATION)
            .email(DEFAULT_EMAIL)
            .rating(DEFAULT_RATING)
            .paidPlan(DEFAULT_PAID_PLAN)
            .imageUrl(DEFAULT_IMAGE_URL);
        return eBCard;
    }

    @Before
    public void initTest() {
        eBCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createEBCard() throws Exception {
        int databaseSizeBeforeCreate = eBCardRepository.findAll().size();

        // Create the EBCard
        EBCardDTO eBCardDTO = eBCardMapper.toDto(eBCard);
        restEBCardMockMvc.perform(post("/api/eb-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBCardDTO)))
            .andExpect(status().isCreated());

        // Validate the EBCard in the database
        List<EBCard> eBCardList = eBCardRepository.findAll();
        assertThat(eBCardList).hasSize(databaseSizeBeforeCreate + 1);
        EBCard testEBCard = eBCardList.get(eBCardList.size() - 1);
        assertThat(testEBCard.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEBCard.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEBCard.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEBCard.getSubtitle()).isEqualTo(DEFAULT_SUBTITLE);
        assertThat(testEBCard.getBussinesName()).isEqualTo(DEFAULT_BUSSINES_NAME);
        assertThat(testEBCard.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testEBCard.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEBCard.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testEBCard.getPaidPlan()).isEqualTo(DEFAULT_PAID_PLAN);
        assertThat(testEBCard.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createEBCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eBCardRepository.findAll().size();

        // Create the EBCard with an existing ID
        eBCard.setId(1L);
        EBCardDTO eBCardDTO = eBCardMapper.toDto(eBCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEBCardMockMvc.perform(post("/api/eb-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EBCard in the database
        List<EBCard> eBCardList = eBCardRepository.findAll();
        assertThat(eBCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEBCards() throws Exception {
        // Initialize the database
        eBCardRepository.saveAndFlush(eBCard);

        // Get all the eBCardList
        restEBCardMockMvc.perform(get("/api/eb-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eBCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE.toString())))
            .andExpect(jsonPath("$.[*].bussinesName").value(hasItem(DEFAULT_BUSSINES_NAME.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].paidPlan").value(hasItem(DEFAULT_PAID_PLAN.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getEBCard() throws Exception {
        // Initialize the database
        eBCardRepository.saveAndFlush(eBCard);

        // Get the eBCard
        restEBCardMockMvc.perform(get("/api/eb-cards/{id}", eBCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eBCard.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.subtitle").value(DEFAULT_SUBTITLE.toString()))
            .andExpect(jsonPath("$.bussinesName").value(DEFAULT_BUSSINES_NAME.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.paidPlan").value(DEFAULT_PAID_PLAN.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEBCard() throws Exception {
        // Get the eBCard
        restEBCardMockMvc.perform(get("/api/eb-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEBCard() throws Exception {
        // Initialize the database
        eBCardRepository.saveAndFlush(eBCard);

        int databaseSizeBeforeUpdate = eBCardRepository.findAll().size();

        // Update the eBCard
        EBCard updatedEBCard = eBCardRepository.findById(eBCard.getId()).get();
        // Disconnect from session so that the updates on updatedEBCard are not directly saved in db
        em.detach(updatedEBCard);
        updatedEBCard
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .title(UPDATED_TITLE)
            .subtitle(UPDATED_SUBTITLE)
            .bussinesName(UPDATED_BUSSINES_NAME)
            .occupation(UPDATED_OCCUPATION)
            .email(UPDATED_EMAIL)
            .rating(UPDATED_RATING)
            .paidPlan(UPDATED_PAID_PLAN)
            .imageUrl(UPDATED_IMAGE_URL);
        EBCardDTO eBCardDTO = eBCardMapper.toDto(updatedEBCard);

        restEBCardMockMvc.perform(put("/api/eb-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBCardDTO)))
            .andExpect(status().isOk());

        // Validate the EBCard in the database
        List<EBCard> eBCardList = eBCardRepository.findAll();
        assertThat(eBCardList).hasSize(databaseSizeBeforeUpdate);
        EBCard testEBCard = eBCardList.get(eBCardList.size() - 1);
        assertThat(testEBCard.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEBCard.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEBCard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEBCard.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testEBCard.getBussinesName()).isEqualTo(UPDATED_BUSSINES_NAME);
        assertThat(testEBCard.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testEBCard.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEBCard.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testEBCard.getPaidPlan()).isEqualTo(UPDATED_PAID_PLAN);
        assertThat(testEBCard.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingEBCard() throws Exception {
        int databaseSizeBeforeUpdate = eBCardRepository.findAll().size();

        // Create the EBCard
        EBCardDTO eBCardDTO = eBCardMapper.toDto(eBCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEBCardMockMvc.perform(put("/api/eb-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EBCard in the database
        List<EBCard> eBCardList = eBCardRepository.findAll();
        assertThat(eBCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEBCard() throws Exception {
        // Initialize the database
        eBCardRepository.saveAndFlush(eBCard);

        int databaseSizeBeforeDelete = eBCardRepository.findAll().size();

        // Delete the eBCard
        restEBCardMockMvc.perform(delete("/api/eb-cards/{id}", eBCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EBCard> eBCardList = eBCardRepository.findAll();
        assertThat(eBCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EBCard.class);
        EBCard eBCard1 = new EBCard();
        eBCard1.setId(1L);
        EBCard eBCard2 = new EBCard();
        eBCard2.setId(eBCard1.getId());
        assertThat(eBCard1).isEqualTo(eBCard2);
        eBCard2.setId(2L);
        assertThat(eBCard1).isNotEqualTo(eBCard2);
        eBCard1.setId(null);
        assertThat(eBCard1).isNotEqualTo(eBCard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EBCardDTO.class);
        EBCardDTO eBCardDTO1 = new EBCardDTO();
        eBCardDTO1.setId(1L);
        EBCardDTO eBCardDTO2 = new EBCardDTO();
        assertThat(eBCardDTO1).isNotEqualTo(eBCardDTO2);
        eBCardDTO2.setId(eBCardDTO1.getId());
        assertThat(eBCardDTO1).isEqualTo(eBCardDTO2);
        eBCardDTO2.setId(2L);
        assertThat(eBCardDTO1).isNotEqualTo(eBCardDTO2);
        eBCardDTO1.setId(null);
        assertThat(eBCardDTO1).isNotEqualTo(eBCardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eBCardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eBCardMapper.fromId(null)).isNull();
    }
}
