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


        //getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);


        for (UserMealWithExceed x:getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000))
            System.out.println("Date Time : "+x.getDateTime() + ". Is exceeded : "+x.isExceed());

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //Sorting by date/time
        Collections.sort(mealList, (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));

        //Array with dates excess calories
        List<LocalDate> localDatesWithExceed=new ArrayList<>();
        LocalDate localDate=mealList.get(0).getDateTime().toLocalDate();
        int caloriesSum=0;


        for (UserMeal x:mealList) {

            if (x.getDateTime().toLocalDate().equals(localDate))
            {
                caloriesSum+=x.getCalories();

                if (caloriesSum>caloriesPerDay)
                    localDatesWithExceed.add(x.getDateTime().toLocalDate());

            }else {
                caloriesSum=x.getCalories();
                localDate=x.getDateTime().toLocalDate();
            }

        }

        //Result array
        List<UserMealWithExceed> userMealWithExceedList=new ArrayList<>();

        //Filling results to array
        for (UserMeal x:mealList)
                if (localDatesWithExceed.contains(x.getDateTime().toLocalDate()))
                    userMealWithExceedList.add(new UserMealWithExceed(x.getDateTime(),x.getDescription(),x.getCalories(),true));
                else
                    userMealWithExceedList.add(new UserMealWithExceed(x.getDateTime(),x.getDescription(),x.getCalories(),false));



        // TODO return filtered list with correctly exceeded field
        return userMealWithExceedList
                .stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(),startTime,endTime))
                .collect(Collectors.toList());
    }
}
