package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dtos.AuthorDto;
import org.example.entities.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-16T01:34:48+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto authorToAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto.AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.name( author.getName() );
        authorDto.email( author.getEmail() );
        authorDto.dateOfBirth( author.getDateOfBirth() );

        return authorDto.build();
    }

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        Author author = new Author();

        author.setName( authorDto.getName() );
        author.setEmail( authorDto.getEmail() );
        author.setDateOfBirth( authorDto.getDateOfBirth() );

        return author;
    }
}
