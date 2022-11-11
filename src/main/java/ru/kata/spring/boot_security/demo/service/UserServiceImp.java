package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RoleService roleService;
    private final UserDao userDao;
    @Autowired
    public UserServiceImp(RoleService roleService, UserDao userDao) {
        this.roleService = roleService;
        this.userDao = userDao;
        addDefaultUser();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.getUserByLogin(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("There is no user with this name");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities(user.getRoles()));
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }
    @Override
    public void updateUser(User user) {
        userDao.updateUser(passwordCoder(user));
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void addDefaultUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findById(1));
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleService.findById(1));
        roleSet2.add(roleService.findById(2));
        User user1 = new User("user", "user", "user", "user", roleSet);
        User user2 = new User("admin", "admin", "admin", "admin", roleSet2);
        save(user1);
        save(user2);
    }

    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> grantedAuthorities(Collection<Role> roles) {
        return roles.stream().map(el -> new SimpleGrantedAuthority(el.getName())).collect(Collectors.toList());
    }
}

