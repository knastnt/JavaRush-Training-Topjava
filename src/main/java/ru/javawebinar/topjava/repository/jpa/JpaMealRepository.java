package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        //Чтобы не делать лишнего обращения в БД и не вытягивать юзера, делаем прокси ссылку
        User ref = em.getReference(User.class, userId);

        if (meal.isNew()){
            meal.setUser(ref);
            em.persist(meal);
        }else{
            if (meal.getUser() == null || userId != meal.getUser().getId()) return null;
            meal = em.merge(meal);
        }
        em.flush();
//        em.detach(meal);
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:user_id")
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createQuery("select m from Meal m where m.id=:id and m.user.id=:user_id", Meal.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        }catch (NoResultException e){
            throw new NotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:id ORDER BY m.dateTime DESC", Meal.class).setParameter("id", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
//        return em.createQuery("SELECT m FROM Meal m WHERE user_id=:id AND m.dateTime BETWEEN :start AND :end ORDER BY m.dateTime DESC", Meal.class)
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:id AND m.dateTime BETWEEN :startDateTime AND :endDateTime", Meal.class)
                .setParameter("id", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}