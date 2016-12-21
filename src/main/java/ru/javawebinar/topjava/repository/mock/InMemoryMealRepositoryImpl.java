package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        // AtomicInteger userCounter=new AtomicInteger(0);
        MealsUtil.MEALS.forEach(meal -> {
            User user = new User();
            // user.setId(userCounter.incrementAndGet());
            user.setId(1);
            meal.setUser(user);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUser(new User());
            meal.getUser().setId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (userId == meal.getUser().getId()) {
            repository.put(meal.getId(), meal);
            return meal;
        }

        return null;

    }

    @Override
    public boolean delete(int id, int userId) {
        if (userId == repository.get(id).getUser().getId()) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);

        if (meal.getUser().getId() != userId)
            return null;

        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = repository.values()
                .stream()
                .filter(meal -> meal.getUser().getId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());

        if (meals.isEmpty())
            return Collections.emptyList();

        return meals;
    }

    public List<Meal> getFilteredByDateTime(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        List<Meal> meals = repository.values()
                .stream()
                .filter(meal -> meal.getUser().getId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenTime(meal.getDateTime().toLocalTime(), fromTime, toTime))
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), fromDate, toDate))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());

        if (meals.isEmpty())
            return Collections.emptyList();

        return meals;

    }


}

