package org.example.controller;

import org.example.dtos.AuthorDto;
import org.example.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")

public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/addAuthor/{id}/")
    public ResponseEntity<AuthorDto> addAuthor(@PathVariable("id") int id, @RequestBody AuthorDto authorDto) {
        AuthorDto returnedAuthorDto = this.authorService.addAuthor(authorDto);
        return ResponseEntity.ok(returnedAuthorDto);
    }

    @GetMapping("/getAuthorByEmail/{email}/")
    public ResponseEntity<AuthorDto> getAuthorByEmail(@PathVariable String email) {
        AuthorDto authorDto = authorService.getAuthorByEmail(email);
        return ResponseEntity.ok().body(authorDto);
    }
}
