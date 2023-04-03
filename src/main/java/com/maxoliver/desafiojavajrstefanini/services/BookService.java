package com.maxoliver.desafiojavajrstefanini.services;

import com.maxoliver.desafiojavajrstefanini.exceptions.NotFoundException;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.respositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book save(Book Book) {
        return bookRepository.save(Book);
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Obra não encontrada"));
    }

    public Book findByName(String name){
        return bookRepository.findFirstByNameContains(name).orElseThrow(() -> new NotFoundException("Obra não encontrada"));
    }

    public void delete(Long id){
        var book = findById(id);
        if(!book.getAuthorList().isEmpty()) {
            book.getAuthorList().forEach(author -> {
                author.getBookList().remove(book);
                authorService.save(author);
            });
        }
        bookRepository.delete(book);
    }

    public boolean existsBookById(Long id){
        return bookRepository.existsById(id);
    }
}
