package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assessment")
@Data
@NoArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String content;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false, referencedColumnName = "id")
    private Course course;

}
