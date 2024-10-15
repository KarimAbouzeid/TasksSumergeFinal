package org.example.mapper;

import org.example.dtos.AuthorDto;
import org.example.entities.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {


    AuthorDto authorToAuthorDto(Author author);



    Author authorDtoToAuthor(AuthorDto authorDto);



}
