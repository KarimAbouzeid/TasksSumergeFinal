package org.example.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "course")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    private String description;

    private int credits;

    @OneToOne(mappedBy = "course")
    private Assessment assessment;


    @OneToMany(mappedBy = "course")
    private Set<Rating> ratings;


    @ManyToMany
    @JoinTable(
            name = "courses_authors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name= "author_id")
    )
    private Set<Author> authors;

}
