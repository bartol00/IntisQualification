package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
@ApplicationScoped
public class User extends PanacheEntity {

    @NotNull
    @Size(min = 3)
    @Column(name = "username")
    private String username;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    public User(){

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
