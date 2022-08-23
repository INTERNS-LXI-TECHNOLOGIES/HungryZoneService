package com.lxisoft.hungryzone.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.hungryzone.IntegrationTest;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.repository.UserExtraRepository;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import com.lxisoft.hungryzone.service.mapper.UserExtraMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserExtraResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_AT_X_AXIS = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_AT_X_AXIS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_AT_Y_AXIS = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_AT_Y_AXIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .locationAtXAxis(DEFAULT_LOCATION_AT_X_AXIS)
            .locationAtYAxis(DEFAULT_LOCATION_AT_Y_AXIS);
        return userExtra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .locationAtXAxis(UPDATED_LOCATION_AT_X_AXIS)
            .locationAtYAxis(UPDATED_LOCATION_AT_Y_AXIS);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();
        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserExtra.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserExtra.getLocationAtXAxis()).isEqualTo(DEFAULT_LOCATION_AT_X_AXIS);
        assertThat(testUserExtra.getLocationAtYAxis()).isEqualTo(DEFAULT_LOCATION_AT_Y_AXIS);
    }

    @Test
    @Transactional
    void createUserExtraWithExistingId() throws Exception {
        // Create the UserExtra with an existing ID
        userExtra.setId(1L);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtraRepository.findAll().size();
        // set the field null
        userExtra.setPhoneNumber(null);

        // Create the UserExtra, which fails.
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtraRepository.findAll().size();
        // set the field null
        userExtra.setAddress(null);

        // Create the UserExtra, which fails.
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationAtXAxisIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtraRepository.findAll().size();
        // set the field null
        userExtra.setLocationAtXAxis(null);

        // Create the UserExtra, which fails.
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationAtYAxisIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtraRepository.findAll().size();
        // set the field null
        userExtra.setLocationAtYAxis(null);

        // Create the UserExtra, which fails.
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].locationAtXAxis").value(hasItem(DEFAULT_LOCATION_AT_X_AXIS)))
            .andExpect(jsonPath("$.[*].locationAtYAxis").value(hasItem(DEFAULT_LOCATION_AT_Y_AXIS)));
    }

    @Test
    @Transactional
    void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.locationAtXAxis").value(DEFAULT_LOCATION_AT_X_AXIS))
            .andExpect(jsonPath("$.locationAtYAxis").value(DEFAULT_LOCATION_AT_Y_AXIS));
    }

    @Test
    @Transactional
    void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .locationAtXAxis(UPDATED_LOCATION_AT_X_AXIS)
            .locationAtYAxis(UPDATED_LOCATION_AT_Y_AXIS);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(updatedUserExtra);

        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserExtra.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserExtra.getLocationAtXAxis()).isEqualTo(UPDATED_LOCATION_AT_X_AXIS);
        assertThat(testUserExtra.getLocationAtYAxis()).isEqualTo(UPDATED_LOCATION_AT_Y_AXIS);
    }

    @Test
    @Transactional
    void putNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra.phoneNumber(UPDATED_PHONE_NUMBER).locationAtYAxis(UPDATED_LOCATION_AT_Y_AXIS);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserExtra.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserExtra.getLocationAtXAxis()).isEqualTo(DEFAULT_LOCATION_AT_X_AXIS);
        assertThat(testUserExtra.getLocationAtYAxis()).isEqualTo(UPDATED_LOCATION_AT_Y_AXIS);
    }

    @Test
    @Transactional
    void fullUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .locationAtXAxis(UPDATED_LOCATION_AT_X_AXIS)
            .locationAtYAxis(UPDATED_LOCATION_AT_Y_AXIS);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserExtra.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserExtra.getLocationAtXAxis()).isEqualTo(UPDATED_LOCATION_AT_X_AXIS);
        assertThat(testUserExtra.getLocationAtYAxis()).isEqualTo(UPDATED_LOCATION_AT_Y_AXIS);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
