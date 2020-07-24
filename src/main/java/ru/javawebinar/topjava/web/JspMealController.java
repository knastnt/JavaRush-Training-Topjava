package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Controller
@RequestMapping("/meals")
public class JspMealController {
    @Autowired
    MealRestController mealController;

    @GetMapping
    public String list(FilterParams filterParams, Model model){
        model.addAttribute("meals", mealController.getBetween(
                filterParams.getStartDate(),
                filterParams.getStartTime(),
                filterParams.getEndDate(),
                filterParams.getEndTime()
        ));
        return "meals";
    }

    @GetMapping({"/{id}", "/new"})
    public String edit(@PathVariable(required = false) Integer id, Model model){
        final Meal meal = id == null ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                mealController.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping({"/{id}", "/new"})
    public String save(@PathVariable(required = false) Integer id, @RequestParam String dateTime, @RequestParam String description, @RequestParam Integer calories){
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (id == null) {
            mealController.create(meal);
        } else {
            mealController.update(meal, id);
        }
        return "redirect:/meals";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id){
        mealController.delete(id);
        return "redirect:/meals";
    }

    private static class FilterParams {
        String startDate;
        String endDate;
        String startTime;
        String endTime;

        public LocalDate getStartDate() {
            return DateTimeUtil.parseLocalDate(startDate);
        }

        public LocalDate getEndDate() {
            return DateTimeUtil.parseLocalDate(endDate);
        }

        public LocalTime getStartTime() {
            return DateTimeUtil.parseLocalTime(startTime);
        }

        public LocalTime getEndTime() {
            return DateTimeUtil.parseLocalTime(endTime);
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
