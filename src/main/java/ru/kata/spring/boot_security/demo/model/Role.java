package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 50 characters")
    private String name;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Collection<User> users;

    public Role() {
    }

    public Role(int id, String role) {
        this.id = id;
        this.name = role;
    }

    public Role(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
