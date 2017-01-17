package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Ivan on 17.01.2017.
 */
@ActiveProfiles(value = {Profiles.ACTIVE_DB, Profiles.JPA})
public class UserServiceTestJpa extends UserServiceTest {
}
