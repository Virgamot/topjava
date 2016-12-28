package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MEAL_0;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Ivan on 27.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @After
    public void tearDown() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL_0.getId(), ADMIN_ID);
        MATCHER.assertEquals(MEAL_0, meal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(MEAL_0.getId(), ADMIN_ID);
        service.get(MEAL_0.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> userMeals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 29, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 10, 0), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_0, MEAL_1, MEAL_2), userMeals);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> collection = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_2, MEAL_1, MEAL_0), collection);

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = service.get(MEAL_0.getId(), ADMIN_ID);
        updated.setDescription("testUpdating");
        updated.setCalories(999);
        service.save(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(MEAL_0.getId(), ADMIN_ID));
    }

    @Test
    public void save() throws Exception {
        Meal meal = new Meal();
        meal.setId(START_SEQ - 1);
        service.save(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        Meal updated = service.get(MEAL_0.getId(), USER_ID);
        updated.setCalories(444);
        service.save(updated, USER_ID);
    }

}