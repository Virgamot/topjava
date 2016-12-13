package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Иван on 12.12.2016.
 */
public class MealDaoImpl implements MealDao {

    private AtomicInteger countId=new AtomicInteger(0);

    private List<Meal> meals=new CopyOnWriteArrayList<>();

    public MealDaoImpl() {

        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        for (Meal meal:mealList)
        {
            meal.setId(new AtomicInteger(countId.incrementAndGet()));
            meals.add(meal);
        }

    }

    @Override
    public void add(Meal meal) {
            meal.setId(new AtomicInteger(countId.incrementAndGet()));
            meals.add(meal);
    }

    @Override
    public void update(Meal meal) {

        remove(meal.getId().intValue());
        meals.add(meal);

    }

    @Override
    public void remove(int id) {

        Meal mealToRemove=null;

        for (Meal meal:meals)
            if (meal.getId().intValue()==id)
            {
                mealToRemove=meal;
            }

            if (mealToRemove!=null)
            meals.remove(mealToRemove);
    }

    @Override
    public Meal getById(int id) {

        for (Meal meal:meals)
            if (meal.getId().intValue()==id)
                return meal;

        return null;
    }

    @Override
    public List<Meal> list() {
        return meals;
    }

}
