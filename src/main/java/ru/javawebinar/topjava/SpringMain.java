package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */

public class SpringMain {


    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));


            System.out.println("\nBeans :\n");
            for (String bean : appCtx.getBeanDefinitionNames())
                System.out.println(bean);
            System.out.println();

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            for (MealWithExceed mealWithExceed : mealRestController.getAll())
                System.out.println(mealWithExceed);

            System.out.println("Getting meal with id=4");
            Meal meal = mealRestController.get(4);
            System.out.println(meal);

            System.out.println("Meal with id=4 deleted");
            mealRestController.delete(4);
            for (MealWithExceed mealWithExceed : mealRestController.getAll())
                System.out.println(mealWithExceed);


            Meal meal1 = new Meal(LocalDateTime.of(2016, Month.DECEMBER, 21, 20, 0), "Тест", 2900);

            System.out.println("Creating meal");
            mealRestController.create(meal1);
            for (MealWithExceed mealWithExceed : mealRestController.getAll())
                System.out.println(mealWithExceed);

            System.out.println(mealRestController.get(7).getUser().getId());

            System.out.println("Filtered by 30 may 10-21");
            LocalDateTime lDT = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
            LocalDateTime lDT2 = LocalDateTime.of(2015, Month.MAY, 30, 21, 0);
            for (MealWithExceed mealWithExceed : mealRestController.getFilteredByDateTime(lDT.toLocalDate(), lDT2.toLocalDate(), lDT.toLocalTime(), lDT2.toLocalTime()))
                System.out.println(mealWithExceed);


        }


    }
}
