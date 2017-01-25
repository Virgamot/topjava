package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by Иван on 25.01.2017.
 */
@Controller
public class MealController {

    @Autowired
    private MealRestController controller;

    @RequestMapping(value = "/meals",method = RequestMethod.GET)
    public String mealList(Model model, HttpServletRequest request)
    {
        String action=request.getParameter("action");

        if (action==null){
            request.setAttribute("meals",controller.getAll());
            model.addAttribute("meals",controller.getAll());
            return "meals";
        }else if ("delete".equals(action)){
            controller.delete(getId(request));
            return "meals";
        }else if ("update".equals(action)){
            model.addAttribute("meal",controller.get(getId(request)));
            return "meal";
        }else if ("create".equals(action)){
            Meal meal=new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",0);
            model.addAttribute("meal",meal);
            return "meal";
        }

        return "meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String updateMeal (Model model, HttpServletRequest request)
    {
        String action=request.getParameter("action");

        if (action==null)
        {
            final Meal meal=new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                                    request.getParameter("description"),
                                    Integer.parseInt(request.getParameter("calories")));

            if(request.getParameter("id").isEmpty())
                controller.create(meal);
            else controller.update(meal,getId(request));
        }

        return "meals";
    }

    private int getId(HttpServletRequest request)
    {
        String id= Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }
}
