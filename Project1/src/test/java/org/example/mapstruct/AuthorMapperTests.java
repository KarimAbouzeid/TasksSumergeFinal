package org.example.mapstruct;

import org.example.dtos.AuthorDto;
import org.example.entities.Author;
import org.example.mapper.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthorMapperTests {

    private AuthorMapper authorMapper;

    private int id;
    private Author author;
    private AuthorDto authorDto;

    @BeforeEach
    public void init(){

        authorMapper = Mappers.getMapper(AuthorMapper.class);

        id = 1;
        authorDto = new AuthorDto();
        authorDto.setName("Karim Khalil");
        authorDto.setEmail("kkhaleel@sumerge.com");
        authorDto.setDateOfBirth(LocalDate.of(2001,7,27));

        author = new Author();
        author.setName("Karim Khalil");
        author.setEmail("kkhaleel@sumerge.com");
        author.setDateOfBirth(LocalDate.of(2001,7,27));
    }

    @Test
    public void AuthorMapper_AuthorToAuthorDto_ReturnsAuthorDto(){

        AuthorDto resultAuthorDto = authorMapper.authorToAuthorDto(author);

        assertNotNull(resultAuthorDto);
        assertEquals(authorDto, resultAuthorDto);

    }

    @Test
    public void AuthorMapper_AuthorDtoToAuthor_ReturnsAuthor(){

        Author resultAuthor = authorMapper.authorDtoToAuthor(authorDto);
        assertNotNull(resultAuthor);
        assertEquals(author, resultAuthor);

    }


}
