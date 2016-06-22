/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.javawebinar.topjava;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserMealRepositoryImpl;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

/**
 *
 * @author vench
 */
public class Test {
    public static void main(String[] s){
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-db.xml", "spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
       
            JdbcUserMealRepositoryImpl t = appCtx.getBean(JdbcUserMealRepositoryImpl.class);
            UserMeal userMeal = new UserMeal();
            userMeal.setCalories(110);
            userMeal.setDateTime(LocalDateTime.now());
            userMeal.setDescription("xx");
            t.save(userMeal, 100000);
        }
    }
}
