package com.guyson.kronos.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(exclude="students")
@ToString(exclude = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moduleID;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Credits is required")
    private int credits;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Lecturer is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturer", referencedColumnName = "lecturerID")
    private Lecturer lecturer;

    @JsonBackReference
    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private Set<User> students;

    private Instant createdAt;

}
