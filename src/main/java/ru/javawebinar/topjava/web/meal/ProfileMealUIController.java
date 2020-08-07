package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "/meals", produces = MediaType.APPLICATION_JSON_VALUE)//, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProfileMealUIController extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

//    @PostMapping
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void createOrUpdate(@RequestBody Meal meal) {
//
////        Meal newMeal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
//        if (meal.isNew()) {
//            super.create(meal);
//        }
//    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(HttpServletRequest request) {
        System.out.println();
    }
}
