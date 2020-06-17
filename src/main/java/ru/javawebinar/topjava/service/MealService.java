package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public Meal create(Meal meal, int userId) {
        if (meal.getUserId() != userId)
            throw new NotFoundException("Saved meal not belongs to you");

        if (!meal.isNew()) {
            //Проверка прав при изменении. Выбрасывает исключение если прав нет на уже существующий Meal в БД
            this.get(meal.getId(), userId);
        }

        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        //Проверка прав при удалении. Выбрасывает исключение если прав нет либо если объекта с таким id нет
        Meal meal = this.get(id, userId);
        repository.delete(id);
    }

    public Meal get(int id, int userId) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        if (userId != meal.getUserId()) throw new NotFoundException("This meal not belongs to you");
        return meal;
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public void update(Meal meal, int userId) {
        this.create(meal, userId);
    }
}