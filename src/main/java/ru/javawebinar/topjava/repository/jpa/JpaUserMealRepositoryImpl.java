package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        
        if(userMeal.getUser() == null) {
            User u = new User();
            u.setId(userId);
            userMeal.setUser(u);
        }
        
        if (userMeal.isNew()) {
            em.persist(userMeal);
            return userMeal;
        } else {
            return em.merge(userMeal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
         return em.createNamedQuery(UserMeal.DELETE)
                 .setParameter("id", id)
                 .setParameter("user_id", userId)
                 .executeUpdate() != 0;
    }

    @Override
  //  @SuppressWarnings("unchecked")
    public UserMeal get(int id, int userId) {
        UserMeal singleResult = em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getSingleResult();
        Hibernate.initialize(singleResult.getUser());
        return singleResult;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_BY_USER_SORTED, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) { 
        return em.createNamedQuery(UserMeal.ALL_BY_USER_BETWEEN_SORTED, UserMeal.class)
                .setParameter("user_id", userId)
                .setParameter("ds", startDate)
                .setParameter("de", endDate)
                .getResultList(); 
    }
}