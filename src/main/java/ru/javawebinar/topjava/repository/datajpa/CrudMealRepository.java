package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int user_id);

    Meal getByIdAndUserId(int id, int user_id);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(int user_id);

    List<Meal> getAllByUserIdAndDateTimeIsGreaterThanEqualAndDateTimeIsLessThanOrderByDateTimeDesc(int user_id, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
