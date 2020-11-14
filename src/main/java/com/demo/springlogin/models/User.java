package com.demo.springlogin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @Length(min = 5, message = "Your username must not be less than 5 characters")
    @NotEmpty(message = "Please enter your username")
    private String username;

    @Column(name = "email")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter a valid email")
    @NotEmpty(message = "Please enter your email")
    private String email;

    @Column(name = "password")
    @Length(min = 8, message = "Your password must not be less than 8 characters")
    @NotEmpty(message = "Please enter your password")
    private String password;

    @Column(name = "first_name")
    @Length(min = 1, message = "Your first name must be at least 1 character")
    @NotEmpty(message = "Please enter your first name")
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 1, message = "Your last name must be at least 1 character")
    @NotEmpty(message = "Please enter your last name")
    private String lastName;

    @Column(name = "active")
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
