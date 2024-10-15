package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


}
