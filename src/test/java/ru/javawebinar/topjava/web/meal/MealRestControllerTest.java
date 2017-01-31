package ru.javawebinar.topjava.web.meal;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

/**
 * Created by Иван on 31.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL=MealRestController.REST_URL+'/';

    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL+MEAL1_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(MATCHER.contentMatcher(MEAL1))
        );
    }

    @Test
    public void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL+MEAL1_ID))
                        .andExpect(status().isOk());

        List<Meal> meals= Arrays.asList(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2);
        MATCHER.assertCollectionEquals(meals,mealService.getAll(AuthorizedUser.id));
    }

    @Test
    public void testGetAll() throws Exception{
        mockMvc.perform(get(REST_URL))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS,2000)));
    }


    @Test
    public void testUpdate() throws Exception{
        Meal updated=new Meal(MEAL1.getId(),MEAL1.getDateTime(),MEAL1.getDescription(),MEAL1.getCalories());
        updated.setDescription("UpdatedName");

        mockMvc.perform(put(REST_URL+MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated,mealService.get(MEAL1_ID,AuthorizedUser.id));
    }

    @Test
    public void testCreate() throws Exception{
        Meal expected=new Meal(null, LocalDateTime.now(),"New",999);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned=MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected,returned);
        List<Meal> meals=new ArrayList<>();
        meals.add(expected);
        meals.addAll(MEALS);
        MATCHER.assertCollectionEquals(meals,mealService.getAll(AuthorizedUser.id));
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .param("startDate", "2015-05-30T12:00:00")
                .param("startTime", "2015-05-30T12:00:00")
                .param("endDate", "2015-05-30T21:00:00")
                .param("endTime", "2015-05-30T21:00:00"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL3, MEAL2),2000))));
    }

}