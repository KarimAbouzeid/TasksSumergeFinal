package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthorDto {

    private String name;
    private String email;
    private LocalDate dateOfBirth;


}
