package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Иван on 10.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "mealEdit.jsp";
    private static String LIST_MEAL = "meals.jsp";
    private MealDao mealDao;

    public MealServlet() {
        super();
        this.mealDao = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forwarding meals");

        String forward = "";
        String action = request.getParameter("action");


        if (action != null && !action.isEmpty()) {
            if (action.equalsIgnoreCase("delete")) {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealDao.remove(mealId);
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000));
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealDao.getById(mealId);
                mealDao.remove(meal.getId().intValue());
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("listMeal")) {
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000));
            } else {
                forward = INSERT_OR_EDIT;
            }
        } else
            forward = LIST_MEAL;

        request.setAttribute("meals", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000));

        request.getRequestDispatcher(forward).forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("HH:mm uuuu-MM-dd", Locale.US)
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime date = LocalDateTime.parse(request.getParameter("dob"), dateTimeFormatter);

        Meal meal = new Meal(LocalDateTime.of(date.toLocalDate(),
                                            date.toLocalTime()),
                                            request.getParameter("mealdescription"),
                                            Integer.parseInt(request.getParameter("calories")));

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
        } else {
            meal.setId(new AtomicInteger(Integer.parseInt(mealId)));
            mealDao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }


}
