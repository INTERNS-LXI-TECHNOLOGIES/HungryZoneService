package com.lxisoft.hungryzone.repository;

import com.lxisoft.hungryzone.domain.Food;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Food entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {}
