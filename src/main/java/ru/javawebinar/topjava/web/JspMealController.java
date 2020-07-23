package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @GetMapping
//    public String list(@RequestParam(required = false) String startDate){
    public String list(FilterParams filterParams){
        return "";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id){
        return "";
    }

    @GetMapping("/new")
    public String create(){
        return "";
    }

    @DeleteMapping("/{id}")
    public String delete(){
        return "";
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
