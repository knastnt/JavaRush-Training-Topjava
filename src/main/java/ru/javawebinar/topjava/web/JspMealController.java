package ru.javawebinar.topjava.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealAbstractController;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Controller
@RequestMapping("/meals")
public class JspMealController extends MealAbstractController {
    static {
        log = LoggerFactory.getLogger(JspMealController.class);
    }

    @GetMapping
    public String list(FilterParams filterParams, Model model){
//        log.debug("Get /meals. " + filterParams); избыточно. это уже логируется стрингом

        model.addAttribute("meals", getBetween(
                filterParams.getStartDate(),
                filterParams.getStartTime(),
                filterParams.getEndDate(),
                filterParams.getEndTime()
        ));
        return "meals";
    }

    @GetMapping({"/{id}", "/new"})
    public String edit(@PathVariable(required = false) Integer id, Model model){
//        log.debug("Get /meals/" + (id==null ? "new" : id)); избыточно. это уже логируется стрингом

        final Meal meal = id == null ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping({"/{id}", "/new"})
    public String save(@PathVariable(required = false) Integer id, @RequestParam String dateTime, @RequestParam String description, @RequestParam Integer calories){
//        log.debug("Post (saving) /meals/" + (id==null ? "new" : id)); избыточно. это уже логируется стрингом

        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (id == null) {
            create(meal);
        } else {
            update(meal, id);
        }
        return "redirect:/meals";
    }

    @PostMapping("/delete")
    public String remove(@RequestParam Integer id){
        log.debug("Удаление Meals с id=" + id);

        delete(id);
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

        @Override
        public String toString() {
            return "FilterParams{" +
                    "startDate='" + startDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }
    }
}
