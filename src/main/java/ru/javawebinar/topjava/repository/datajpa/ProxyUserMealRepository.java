/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.javawebinar.topjava.repository.datajpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

/**
 *
 * @author vench
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer>{
  
    @Transactional
    @Modifying 
    @Query("DELETE FROM UserMeal WHERE id=:id AND user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal meal);
    
  

    
    @Query("FROM UserMeal WHERE id=:id AND user.id=:userId")
    UserMeal loadOne(@Param("id") int id, @Param("userId") int userId);

    @Query("FROM UserMeal WHERE user.id=:userId ORDER BY date_time DESC")
    List<UserMeal> findAllByUser(@Param("userId") int userId);
  
    @Query("FROM UserMeal WHERE user.id=:userId AND date_time BETWEEN :startDate AND :endDate ORDER BY date_time DESC")
    List<UserMeal> findBetweenByUser(@Param("startDate") LocalDateTime startDate,  @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
