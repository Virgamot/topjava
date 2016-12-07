package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.JUNE,11,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE,11,11,59), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE,11,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,20,0), "Ужин", 800),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map=new HashMap<>();

        for (UserMeal meal:mealList)
            map.merge(meal.getDateTime().toLocalDate(),meal.getCalories(), (calories,overCalories) -> (calories+overCalories));


        List<UserMealWithExceed> list=new ArrayList<>();


       for (UserMeal meal:mealList)
               if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime))
               {
                   boolean isExceed= map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ;
                   list.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),isExceed));
               }


        return list;

    }
}
