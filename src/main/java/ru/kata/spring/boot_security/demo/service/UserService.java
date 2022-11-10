package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(int id);

    User findByUsername(String username);

    void addDefaultUser();

    void save(User user);

    void updateUser(User user);

    void delete(int id);

    Collection<? extends GrantedAuthority> grantedAuthorities(Collection<Role> roles);
}
