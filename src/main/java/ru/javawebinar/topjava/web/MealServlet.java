package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Spring инициализация
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class); //Autowired не работает, т.к. этот сервлет не помечен стереотипом

        MealsUtil.MEALS.forEach(meal -> mealRestController.create(meal));
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(
                                mealRestController.getAll(getStartDate(request),getEndDate(request)),
                                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                                getStartTime(request),
                                getEndTime(request)
                        ));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }



    private LocalDate getStartDate(HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        return parseLocalDate(startDate, LocalDate.MIN);
    }

    private LocalDate getEndDate(HttpServletRequest request) {
        String endDate = request.getParameter("endDate");
        return parseLocalDate(endDate, LocalDate.MAX);
    }

    private LocalTime getStartTime(HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        return parseLocalTime(startTime, LocalTime.MIN);
    }

    private LocalTime getEndTime(HttpServletRequest request) {
        String endTime = request.getParameter("endTime");
        return parseLocalTime(endTime, LocalTime.MAX);
    }



    private LocalDate parseLocalDate(String s, LocalDate defaultIfException){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][dd.MM.yyyy][dd,MM,yyyy][yyyy-MM-dd][yyyy.MM.dd][yyyy,MM,dd]");
        try {
            return LocalDate.parse(s, formatter);
        }catch (NullPointerException | DateTimeParseException e){
            return defaultIfException;
        }
    }

    private LocalTime parseLocalTime(String s, LocalTime defaultIfException){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[HH:mm][HH-mm][HH.mm][HH,mm]");
        try {
            return LocalTime.parse(s, formatter);
        }catch (NullPointerException | DateTimeParseException e){
            return defaultIfException;
        }
    }
}
