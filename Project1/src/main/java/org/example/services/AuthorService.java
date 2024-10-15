package org.example.services;

import org.example.dtos.AuthorDto;
import org.example.entities.Author;
import org.example.exception.AuthorNotFoundException;
import org.example.filter.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan({"org.example.repository", "org.example.mapper"})
public class AuthorService {

    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }


    public AuthorDto getAuthorByEmail(String email)  {

        Author author =
                authorRepository
                .findAuthorByEmail(email)
                .orElseThrow(()-> new AuthorNotFoundException("Author not found"));

        return authorMapper.authorToAuthorDto(author);
    }

    public AuthorDto addAuthor(AuthorDto authorDto){
        Author author = authorMapper.authorDtoToAuthor(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.authorToAuthorDto(savedAuthor);
    }




}
