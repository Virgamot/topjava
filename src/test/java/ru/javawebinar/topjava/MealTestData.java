package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    /*public static final int MEAL0_ID_ADMIN =START_SEQ;
    public static final int MEAL1_ID_ADMIN=START_SEQ+1;
    public static final int MEAL2_ID_ADMIN=START_SEQ+2;
    public static final int MEAL0_ID_USER=START_SEQ+3;
    public static final int MEAL1_ID_USER=START_SEQ+4;
    public static final int MEAL2_ID_USER=START_SEQ+5;*/

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;


    public static final Meal MEAL_0 = new Meal(START_SEQ, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_1 = new Meal(START_SEQ+1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_2 = new Meal(START_SEQ+2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_3 = new Meal(START_SEQ+3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_4 = new Meal(START_SEQ+4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_5 = new Meal(START_SEQ+5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(((expected, actual) -> expected == actual ||
            (Objects.equals(expected.getDateTime(),actual.getDateTime())
            &&Objects.equals(expected.getCalories(),actual.getCalories())
            &&Objects.equals(expected.getDescription(),actual.getDescription())
            &&Objects.equals(expected.getId(),actual.getId()))));

}
