package com.lxisoft.hungryzone.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.hungryzone.IntegrationTest;
import com.lxisoft.hungryzone.domain.CartItem;
import com.lxisoft.hungryzone.repository.CartItemRepository;
import com.lxisoft.hungryzone.service.dto.CartItemDTO;
import com.lxisoft.hungryzone.service.mapper.CartItemMapper;
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
 * Integration tests for the {@link CartItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CartItemResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/cart-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartItemMockMvc;

    private CartItem cartItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartItem createEntity(EntityManager em) {
        CartItem cartItem = new CartItem().quantity(DEFAULT_QUANTITY);
        return cartItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartItem createUpdatedEntity(EntityManager em) {
        CartItem cartItem = new CartItem().quantity(UPDATED_QUANTITY);
        return cartItem;
    }

    @BeforeEach
    public void initTest() {
        cartItem = createEntity(em);
    }

    @Test
    @Transactional
    void createCartItem() throws Exception {
        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();
        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);
        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate + 1);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createCartItemWithExistingId() throws Exception {
        // Create the CartItem with an existing ID
        cartItem.setId(1L);
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartItemRepository.findAll().size();
        // set the field null
        cartItem.setQuantity(null);

        // Create the CartItem, which fails.
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isBadRequest());

        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCartItems() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList
        restCartItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get the cartItem
        restCartItemMockMvc
            .perform(get(ENTITY_API_URL_ID, cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingCartItem() throws Exception {
        // Get the cartItem
        restCartItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Update the cartItem
        CartItem updatedCartItem = cartItemRepository.findById(cartItem.getId()).get();
        // Disconnect from session so that the updates on updatedCartItem are not directly saved in db
        em.detach(updatedCartItem);
        updatedCartItem.quantity(UPDATED_QUANTITY);
        CartItemDTO cartItemDTO = cartItemMapper.toDto(updatedCartItem);

        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartItemWithPatch() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Update the cartItem using partial update
        CartItem partialUpdatedCartItem = new CartItem();
        partialUpdatedCartItem.setId(cartItem.getId());

        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCartItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCartItem))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateCartItemWithPatch() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Update the cartItem using partial update
        CartItem partialUpdatedCartItem = new CartItem();
        partialUpdatedCartItem.setId(cartItem.getId());

        partialUpdatedCartItem.quantity(UPDATED_QUANTITY);

        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCartItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCartItem))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cartItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();
        cartItem.setId(count.incrementAndGet());

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cartItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        int databaseSizeBeforeDelete = cartItemRepository.findAll().size();

        // Delete the cartItem
        restCartItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, cartItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
