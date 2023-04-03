package com.maxoliver.desafiojavajrstefanini.controller;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestBookDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseBookDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.RegisterAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
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
@RequestMapping(path = "/stefannini/library/books")
@RequiredArgsConstructor
public class BooksController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ResponseBookDTO>> findAll() {
        List<ResponseBookDTO> books = bookService.findAll()
                .stream()
                .map(book -> modelMapper.map(book, ResponseBookDTO.class))
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<ResponseBookDTO> findById(@PathVariable @Valid Long id) {
        var response = modelMapper.map(bookService.findById(id), ResponseBookDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book")
    public ResponseEntity<ResponseBookDTO> findByName(@RequestParam String name) {
        var response = modelMapper.map(bookService.findByName(name), ResponseBookDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseBookDTO> save(@RequestBody @Valid RequestBookDTO requestBookDTO) {
        var book = modelMapper.map(requestBookDTO, Book.class);
        var response = modelMapper.map(bookService.save(book), ResponseBookDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/book/{id}")
    @Transactional
    public ResponseEntity<ResponseBookDTO> update(@PathVariable Long id, @RequestBody @Valid RequestBookDTO requestBookDTO) {
        var book = modelMapper.map(requestBookDTO, Book.class);
        if(bookService.existsBookById(id)){
            book.setId(id);
        }
        var response = modelMapper.map(bookService.save(book), ResponseBookDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("book/{id}/author")
    @Transactional
    public ResponseEntity<ResponseBookDTO> registerAuthors(@PathVariable Long id, @RequestBody @Valid RegisterAuthorDTO authorDTO) {
        var book = bookService.findById(id);
        var author = authorService.findById(authorDTO.getId());
        author.getBookList().add(book);
        book.getAuthorList().add(author);
        var response = modelMapper.map(book, ResponseBookDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/book/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
