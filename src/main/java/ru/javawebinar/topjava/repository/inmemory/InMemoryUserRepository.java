package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser)->user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return new ArrayList<>(repository.values())
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(user->user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        userRepository.getAll().forEach(user-> System.out.println(user.toString()));
        userRepository.delete(1);
        userRepository.getAll().forEach(user-> System.out.println(user.toString()));
        System.out.println(userRepository.get(2));
        System.out.println(userRepository.get(1));
        System.out.println(userRepository.getByEmail("anna@mail.ru"));
        System.out.println(userRepository.getByEmail("ann@mail.ru"));
        userRepository.save(new User(null, "Зоя", "zoya@mail.ru", "123", Role.ROLE_USER));
        userRepository.save(new User(6, "Кирилл", "kirill@mail.ru", "123", Role.ROLE_USER));
        userRepository.getAll().forEach(user-> System.out.println(user.toString()));

    }
}
