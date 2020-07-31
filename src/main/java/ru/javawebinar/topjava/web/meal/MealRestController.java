package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @GetMapping
    @Override
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/filter")
    public List<MealTo> getBetweenStrings(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String endTime
    ) {
        LocalDate _startDate = parseLocalDate(startDate);
        LocalDate _endDate = parseLocalDate(endDate);
        LocalTime _startTime = parseLocalTime(startTime);
        LocalTime _endTime = parseLocalTime(endTime);
        return super.getBetween(_startDate, _startTime, _endDate, _endTime);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }
}