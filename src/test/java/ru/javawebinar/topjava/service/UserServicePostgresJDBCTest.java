package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.POSTGRES_DB, Profiles.JDBC})
public class UserServicePostgresJDBCTest extends UserServiceTest {
}
