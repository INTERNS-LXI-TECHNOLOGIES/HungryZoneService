package com.lxisoft.hungryzone.service;

import com.lxisoft.hungryzone.service.dto.CartItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.hungryzone.domain.CartItem}.
 */
public interface CartItemService {
    /**
     * Save a cartItem.
     *
     * @param cartItemDTO the entity to save.
     * @return the persisted entity.
     */
    CartItemDTO save(CartItemDTO cartItemDTO);

    /**
     * Updates a cartItem.
     *
     * @param cartItemDTO the entity to update.
     * @return the persisted entity.
     */
    CartItemDTO update(CartItemDTO cartItemDTO);

    /**
     * Partially updates a cartItem.
     *
     * @param cartItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CartItemDTO> partialUpdate(CartItemDTO cartItemDTO);

    /**
     * Get all the cartItems.
     *
     * @return the list of entities.
     */
    List<CartItemDTO> findAll();

    /**
     * Get the "id" cartItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartItemDTO> findOne(Long id);

    /**
     * Delete the "id" cartItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
