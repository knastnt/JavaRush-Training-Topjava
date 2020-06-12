package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Initializier;
import ru.javawebinar.topjava.dao.MealsDAO;
import ru.javawebinar.topjava.dao.MealsDAOFabric;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealsDAO mealsDAO = MealsDAOFabric.getMealsDAO();

    private static String INSERT_OR_EDIT = "/mealEdit.jsp";
    private static String LIST_MEALS = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String edit = request.getParameter("edit");
        String delete = request.getParameter("delete");


        if (delete != null) {
            mealsDAO.deleteMealById(Long.parseLong(delete));
            //Редиректим сюда же, но без Query https://stackoverflow.com/questions/16675191/get-full-url-and-query-string-in-servlet-for-both-http-and-https-requests/16675399
            response.sendRedirect(request.getRequestURL().toString());
            return;
        }
        if (edit == null) {
            showMealsList(request, response);
        }else{
            showEditForm(Long.parseLong(edit), request, response);
        }
//        response.sendRedirect("meals.jsp");
    }

    private void showMealsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int caloriesPerDay = 2000;
        request.setAttribute("caloriesPerDay", caloriesPerDay);

        List<Meal> allMeals = mealsDAO.getAllMeals();
        request.setAttribute("mealsTo", MealsUtil.filteredByTime(allMeals, null, null, caloriesPerDay));

        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }

    private void showEditForm(long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal;
        if (id>0) {
            meal = mealsDAO.getMealById(id);
        }else{
            meal = new Meal(null, LocalDateTime.now(), null, null);
        }

        request.setAttribute("mealTo", MealsUtil.createTo(meal, false));

        request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
    }
}
