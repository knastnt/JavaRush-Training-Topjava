package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public Meal create(Meal meal, int userId) {
        //Проверяем что объект новый и кидаем IllegalArgumentException если не новый
        ValidationUtil.checkNew(meal);
        //Преверяем что userId сохраняемого Meal соответствует аутентифицированному пользователю
        return ValidationUtil.checkNotFound(repository.save(meal, userId), "New entity not belongs to authenticated user");
    }

    public void delete(int id, int userId) {
        //Бросаем NotFoundException если не найдено
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        //Бросаем NotFoundException если не найдено
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAll(userId, startDate, endDate);
    }

    public void update(Meal meal, int userId) {
        //Преверяем что userId обновляемого Meal соответствует аутентифицированному пользователю
        ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}