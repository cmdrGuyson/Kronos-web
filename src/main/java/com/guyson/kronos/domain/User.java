package com.guyson.kronos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user")
public class User {
    @Id
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Role is required")
    private String role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "_class", referencedColumnName = "classID")
    private Class _class;

    @ManyToMany
    @JoinTable(
            name = "student_module",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "moduleID"))
    private Set<Module> modules;

    private Instant createdAt;

    public void addModule(Module module) {
        modules.add(module);
    }

}
