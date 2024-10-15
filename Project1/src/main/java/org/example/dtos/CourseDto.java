package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CourseDto {


    private int id;

    @NotNull
    @NotBlank(message = "Name cannot be empty")
    private String name;


    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Name cannot be empty")
    private String description;

    @NotNull(message = "Credits cannot be empty")
    @Positive(message = "Credits must be a positive number")
    private Integer credits;
}