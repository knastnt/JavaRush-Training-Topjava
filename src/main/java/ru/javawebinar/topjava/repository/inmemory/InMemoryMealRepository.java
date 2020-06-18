package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);

        //Проверяем что у сохраняемого Meal такой же userId
        if (meal.getUserId() != userId) return null;

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }

        //Проверяем, что у заменяемого Meal  такой же userId
        Meal oldMeal = repository.get(meal.getId());
        if (oldMeal != null && oldMeal.getUserId() != userId) return null;

        repository.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);

        //Проверяем, что у удаляемого Meal  такой же userId
        Meal oldMeal = repository.get(id);
        if (oldMeal != null && oldMeal.getUserId() != userId) return false;

        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        //Проверяем, что у получаемого Meal такой же userId
        if (meal != null && meal.getUserId() == userId) {
            return meal;
        }else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId) //Проверяем, что у получаемого Meal такой же userId
                .sorted((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()))
                .collect(Collectors.toList());
    }

}

