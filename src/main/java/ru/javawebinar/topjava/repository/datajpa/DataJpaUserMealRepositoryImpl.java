package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
//@Profile(Profiles.DATAJPA)
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
     
    
    @Autowired
    private ProxyUserMealRepository proxy;
    
    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if(!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            throw new NotFoundException("Error access: " + userId); 
        }
        if(userMeal.getUser() == null) {
            userMeal.setUser(new User(userId));
        }
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) > 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.loadOne(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAllByUser(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findBetweenByUser(startDate, endDate, userId);
    }
}
