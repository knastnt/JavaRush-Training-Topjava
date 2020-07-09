package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.HSQL_DB, Profiles.JDBC})
public class MealServiceHsqlJDBCTest extends MealServiceTest {
}
