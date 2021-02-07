package com.guyson.kronos.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(exclude="modules")
@ToString(exclude = "modules")
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

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_module",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "moduleID"))
    private Set<Module> modules;

    private Instant createdAt;

    public void addModule(Module module) {
        if(!modules.contains(module)) {
            modules.add(module);
        }
    }

    public void removeModule(Module module) {
        if(modules.contains(module)) {
            modules.remove(module);
        }
    }

}
