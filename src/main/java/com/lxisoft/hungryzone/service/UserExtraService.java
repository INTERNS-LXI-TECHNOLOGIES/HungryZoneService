package com.lxisoft.hungryzone.service;

import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.hungryzone.domain.UserExtra}.
 */
public interface UserExtraService {
    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save.
     * @return the persisted entity.
     */
    UserExtraDTO save(UserExtraDTO userExtraDTO);

    /**
     * Updates a userExtra.
     *
     * @param userExtraDTO the entity to update.
     * @return the persisted entity.
     */
    UserExtraDTO update(UserExtraDTO userExtraDTO);

    /**
     * Partially updates a userExtra.
     *
     * @param userExtraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserExtraDTO> partialUpdate(UserExtraDTO userExtraDTO);

    /**
     * Get all the userExtras.
     *
     * @return the list of entities.
     */
    List<UserExtraDTO> findAll();
    /**
     * Get all the UserExtraDTO where Cart is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserExtraDTO> findAllWhereCartIsNull();

    /**
     * Get the "id" userExtra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExtraDTO> findOne(Long id);

    /**
     * Delete the "id" userExtra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
