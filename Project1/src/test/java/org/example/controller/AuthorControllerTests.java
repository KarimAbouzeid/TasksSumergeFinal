package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dtos.AuthorDto;
import org.example.entities.Author;
import org.example.exception.AuthorNotFoundException;
import org.example.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Author author;
    private AuthorDto authorDto;
    private int id;

    @BeforeEach
    public void init(){

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
    public void AuthorController_AddAuthor_ReturnAuthorDto() throws Exception {

        when(authorService.addAuthor(any(AuthorDto.class))).thenReturn(authorDto);

        ResultActions resultActions = mockMvc.perform(post("/author/addAuthor/{id}/", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto)));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.name").value(authorDto.getName()));

    }

    @Test
    public void AuthorController_GetAuthorByEmail_ReturnAuthorDto() throws Exception {

        when(authorService.getAuthorByEmail(argThat(email -> email.contains("@")))).thenReturn(authorDto);

        ResultActions resultActions = mockMvc.perform(get("/author/getAuthorByEmail/{email}/", authorDto.getEmail())
        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.name").value(authorDto.getName()));
        resultActions.andExpect(jsonPath("$.email").value(authorDto.getEmail()));
    }

    @Test
    public void AuthorController_AuthorNotFound_ReturnsString() throws Exception{

        AuthorNotFoundException authorNotFoundException = new AuthorNotFoundException("Author not found");
        when(authorService.getAuthorByEmail(any(String.class))).thenThrow(authorNotFoundException);

        mockMvc.perform(get("/author/getAuthorByEmail/{email}/", authorDto.getEmail()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author not found"));

    }
}
