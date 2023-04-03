package com.maxoliver.desafiojavajrstefanini.controller;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.RegisterBookDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.services.AuthorService;
import com.maxoliver.desafiojavajrstefanini.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stefannini/library/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<ResponseAuthorDTO>> findAll(){
        List<ResponseAuthorDTO> authors = authorService.findAll()
                .stream()
                .map(author -> modelMapper.map(author, ResponseAuthorDTO.class))
                .toList();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ResponseAuthorDTO> findById(@PathVariable @Valid Long id){
        var response = modelMapper.map(authorService.findById(id), ResponseAuthorDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/author")
    public ResponseEntity<ResponseAuthorDTO> findByName(@RequestParam String name){
        var response = modelMapper.map(authorService.findByName(name), ResponseAuthorDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseAuthorDTO> save(@RequestBody @Valid RequestAuthorDTO requestAuthorDTO){
        var author = modelMapper.map(requestAuthorDTO, Author.class);
        var response = modelMapper.map(authorService.save(author), ResponseAuthorDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/author/{id}")
    @Transactional
    public ResponseEntity<ResponseAuthorDTO> update(@PathVariable Long id, @RequestBody @Valid RequestAuthorDTO authorDTO){
        var author = modelMapper.map(authorDTO, Author.class);
        if(authorService.exitsAuthorById(id)){
            author.setId(id);
        }
        var response = modelMapper.map(authorService.save(author), ResponseAuthorDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("author/{id}/book")
    @Transactional
    public ResponseEntity<ResponseAuthorDTO> registerBooks(@PathVariable Long id, @RequestBody @Valid RegisterBookDTO bookDTO){
        var author = authorService.findById(id);
        var book = bookService.findById(bookDTO.getId());
        author.getBookList().add(book);
        var response = modelMapper.map(author, ResponseAuthorDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/author/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id){
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
