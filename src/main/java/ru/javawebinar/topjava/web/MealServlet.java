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

        String action = request.getParameter("action");

        if (action == null) {
            int caloriesPerDay = 2000;
            request.setAttribute("caloriesPerDay", caloriesPerDay);

            List<Meal> allMeals = mealsDAO.getAllMeals();
            request.setAttribute("mealsTo", MealsUtil.filteredByTime(allMeals, null, null, caloriesPerDay));

            request.getRequestDispatcher(LIST_MEALS).forward(request, response);
        }else{
            switch (action.toLowerCase()){
                case "new":

                    break;
            }
        }
//        response.sendRedirect("meals.jsp");
    }
}
