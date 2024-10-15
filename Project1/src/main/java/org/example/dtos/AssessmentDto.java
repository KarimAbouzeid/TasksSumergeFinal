package org.example.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentDto {

    private int id;
    private String content;
    private int course;

}
