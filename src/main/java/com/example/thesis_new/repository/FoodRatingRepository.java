package com.example.thesis_new.repository;

import com.example.thesis_new.entity.FoodRating;
import com.example.thesis_new.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRatingRepository extends JpaRepository<FoodRating, Long> {

    @Query("SELECT AVG(fr.service) FROM FoodRating fr WHERE fr.foodRating.id = ?1")
    Double getAvgOfPackagedRating(Long foodId);

    @Query("SELECT AVG(fr.quality) FROM FoodRating fr WHERE fr.foodRating.id = ?1")
    Double getAvgOfQualityRating(Long foodId);

    @Query("SELECT AVG(fr.price) FROM FoodRating fr WHERE fr.foodRating.id = ?1")
    Double getAvgOfPriceRating(Long foodId);

    @Query("select fr from FoodRating fr where fr.customer.id = ?1 and fr.foodRating.id = ?2")
    FoodRating findByCustomerIdAndFoodId(Long customerId, Long foodId);

    @Query("DELETE FROM FoodRating fr WHERE fr.customer.id = ?1 and fr.foodRating.id = ?2")
    void deleteByCustomerIdAndFoodId(Long customerId, Long foodId);
}
