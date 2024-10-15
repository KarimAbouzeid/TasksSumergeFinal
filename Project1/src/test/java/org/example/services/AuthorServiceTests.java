package org.example.services;


import org.example.exception.AuthorNotFoundException;
import org.example.dtos.AuthorDto;
import org.example.entities.Author;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorDto authorDto;

    @BeforeEach
    public void init(){
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
    public void AuthorService_AddAuthor_ReturnsAuthorDto(){
        when(authorMapper.authorDtoToAuthor(authorDto)).thenReturn(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        AuthorDto resultDto = authorService.addAuthor(authorDto);

        Assertions.assertEquals(resultDto, authorDto);
    }

    @Test
    public void AuthorService_GetAuthorByEmail_ReturnsAuthorDto(){
        when(authorRepository.findAuthorByEmail(argThat(email -> email.contains("@")))).thenReturn(Optional.of(author));
        when(authorMapper.authorToAuthorDto(any(Author.class))).thenReturn(authorDto);
        AuthorDto resultDto = authorService.getAuthorByEmail(authorDto.getEmail());

        Assertions.assertEquals(resultDto, authorDto);

    }

    @Test
    public void AuthorService_FindAuthorByEmail_ThrowsAuthorNotFoundException(){
        when(authorRepository.findAuthorByEmail(argThat(email -> email.contains("@")))).thenThrow(AuthorNotFoundException.class);
        Assertions.assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorByEmail(authorDto.getEmail()));
    }
}
