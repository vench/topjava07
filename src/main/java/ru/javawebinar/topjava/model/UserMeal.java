package ru.javawebinar.topjava.model;

import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal u WHERE u.id=:id AND u.user.id=:user_id"), 
        @NamedQuery(name = UserMeal.GET, query = "SELECT u FROM UserMeal u WHERE u.id=:id AND u.user.id=:user_id"),
    //TODO Странная сортировка. но без нее тест не пашет
    //TODO В теории для    user ожно добавить FETCH
        @NamedQuery(name = UserMeal.ALL_BY_USER_SORTED, 
                    query = "SELECT u FROM UserMeal u INNER JOIN  u.user WHERE u.user.id=:user_id ORDER BY u.id DESC"),
        @NamedQuery(name = UserMeal.ALL_BY_USER_BETWEEN_SORTED, 
                    query = "SELECT u FROM UserMeal u INNER JOIN  u.user WHERE u.user.id=:user_id AND u.dateTime BETWEEN :ds AND :de  ORDER BY u.id DESC"),
    
})

@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_BY_USER_SORTED = "UserMeal.allByUserSorted";
    public static final String GET = "UserMeal.get";
    public static final String ALL_BY_USER_BETWEEN_SORTED = "UserMeal.allByUserBetweenSorted";

   // @DateTimeFormat(iso = ISO.DATE_TIME)
    @Type(type="ru.javawebinar.topjava.util.hibernate.type.LocalDateTimeUserType") 
    @Column(name = "date_time", nullable = false) 
    //@NotEmpty
    private  LocalDateTime dateTime;

    @Column(name = "description", nullable = false) 
    @NotEmpty
    private  String description;

    @Column(name = "calories", nullable = false) 
    //@NotEmpty
    private  int calories;
     

    @JoinColumn(name = "user_id", nullable=false)
    @ManyToOne(fetch = FetchType.LAZY) 
    private User user;
    
    
    public UserMeal() {}  

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }


    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
