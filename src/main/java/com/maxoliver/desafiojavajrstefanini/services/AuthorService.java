package com.maxoliver.desafiojavajrstefanini.services;

import com.maxoliver.desafiojavajrstefanini.exceptions.BadRequestException;
import com.maxoliver.desafiojavajrstefanini.exceptions.EntityInUseException;
import com.maxoliver.desafiojavajrstefanini.exceptions.NotFoundException;
import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.respositories.AuthorRepository;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author save(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage().contains("EMAIL NULLS FIRST") ? "Email já cadastrado" : "Cpf já cadastrado";
            throw new BadRequestException(message);
        }
    }

    public Author findById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Autor não encontrado"));
    }

    public Author findByName(String name){
        return authorRepository.findFirstByNameContains(name).orElseThrow(() -> new NotFoundException("Autor não encontrado"));
    }

    public void delete(Long id){
        var author = findById(id);
        if(!author.getBookList().isEmpty()){
            throw new EntityInUseException("O autor está associado a uma ou mais obras e não pôde ser excluído");
        }
        authorRepository.delete(author);
    }

    public boolean exitsAuthorById(Long id){
        return authorRepository.existsById(id);
    }
}
