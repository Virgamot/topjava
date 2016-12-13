package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Иван on 12.12.2016.
 */
public interface MealDao {
     void add(Meal meal);

     void update(Meal meal);

     void remove(int id);

     Meal getById(int id);

     List<Meal> list();
}
