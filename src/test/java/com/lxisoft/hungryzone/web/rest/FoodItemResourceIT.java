package com.lxisoft.hungryzone.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.hungryzone.IntegrationTest;
import com.lxisoft.hungryzone.domain.FoodItem;
import com.lxisoft.hungryzone.repository.FoodItemRepository;
import com.lxisoft.hungryzone.service.dto.FoodItemDTO;
import com.lxisoft.hungryzone.service.mapper.FoodItemMapper;
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
 * Integration tests for the {@link FoodItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoodItemResourceIT {

    private static final Integer DEFAULT_QUANDITY = 1;
    private static final Integer UPDATED_QUANDITY = 2;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/food-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private FoodItemMapper foodItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodItemMockMvc;

    private FoodItem foodItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem().quandity(DEFAULT_QUANDITY).unit(DEFAULT_UNIT);
        return foodItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createUpdatedEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem().quandity(UPDATED_QUANDITY).unit(UPDATED_UNIT);
        return foodItem;
    }

    @BeforeEach
    public void initTest() {
        foodItem = createEntity(em);
    }

    @Test
    @Transactional
    void createFoodItem() throws Exception {
        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();
        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);
        restFoodItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate + 1);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getQuandity()).isEqualTo(DEFAULT_QUANDITY);
        assertThat(testFoodItem.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    void createFoodItemWithExistingId() throws Exception {
        // Create the FoodItem with an existing ID
        foodItem.setId(1L);
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuandityIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodItemRepository.findAll().size();
        // set the field null
        foodItem.setQuandity(null);

        // Create the FoodItem, which fails.
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        restFoodItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodItemRepository.findAll().size();
        // set the field null
        foodItem.setUnit(null);

        // Create the FoodItem, which fails.
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        restFoodItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFoodItems() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList
        restFoodItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quandity").value(hasItem(DEFAULT_QUANDITY)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }

    @Test
    @Transactional
    void getFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get the foodItem
        restFoodItemMockMvc
            .perform(get(ENTITY_API_URL_ID, foodItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodItem.getId().intValue()))
            .andExpect(jsonPath("$.quandity").value(DEFAULT_QUANDITY))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT));
    }

    @Test
    @Transactional
    void getNonExistingFoodItem() throws Exception {
        // Get the foodItem
        restFoodItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Update the foodItem
        FoodItem updatedFoodItem = foodItemRepository.findById(foodItem.getId()).get();
        // Disconnect from session so that the updates on updatedFoodItem are not directly saved in db
        em.detach(updatedFoodItem);
        updatedFoodItem.quandity(UPDATED_QUANDITY).unit(UPDATED_UNIT);
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(updatedFoodItem);

        restFoodItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getQuandity()).isEqualTo(UPDATED_QUANDITY);
        assertThat(testFoodItem.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void putNonExistingFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoodItemWithPatch() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Update the foodItem using partial update
        FoodItem partialUpdatedFoodItem = new FoodItem();
        partialUpdatedFoodItem.setId(foodItem.getId());

        partialUpdatedFoodItem.quandity(UPDATED_QUANDITY).unit(UPDATED_UNIT);

        restFoodItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodItem))
            )
            .andExpect(status().isOk());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getQuandity()).isEqualTo(UPDATED_QUANDITY);
        assertThat(testFoodItem.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void fullUpdateFoodItemWithPatch() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Update the foodItem using partial update
        FoodItem partialUpdatedFoodItem = new FoodItem();
        partialUpdatedFoodItem.setId(foodItem.getId());

        partialUpdatedFoodItem.quandity(UPDATED_QUANDITY).unit(UPDATED_UNIT);

        restFoodItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodItem))
            )
            .andExpect(status().isOk());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getQuandity()).isEqualTo(UPDATED_QUANDITY);
        assertThat(testFoodItem.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void patchNonExistingFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foodItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();
        foodItem.setId(count.incrementAndGet());

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(foodItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeDelete = foodItemRepository.findAll().size();

        // Delete the foodItem
        restFoodItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, foodItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
