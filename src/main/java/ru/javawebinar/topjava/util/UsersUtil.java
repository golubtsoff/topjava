package ru.javawebinar.topjava.util;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Иван", "ivan@mail.ru", "123", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null, "Пётр", "petr@mail.ru", "123", Role.ROLE_USER),
            new User(null, "Ольга", "olga@mail.ru", "123", Role.ROLE_USER),
            new User(null, "Анна", "anna@mail.ru", "123", Role.ROLE_USER),
            new User(null, "Инна", "inna@mail.ru", "123", Role.ROLE_ADMIN),
            new User(null, "Вася", "vasia@mail.ru", "123", Role.ROLE_USER)
    );
}
