package com.maxoliver.desafiojavajrstefanini.util;

import com.maxoliver.desafiojavajrstefanini.dtos.RegisterBookDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.RequestBookDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseBookDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;

import java.time.LocalDate;
import java.util.HashSet;

public class BookFactory {

    public static Book bookNoId(){
        return Book.builder()
                .name("The Lord of the Rings")
                .description("The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1954,07,29))
                .build();
    }

    public static Book book2NoId(){
        return Book.builder()
                .name("The Hobbit")
                .description("The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1937,9,21))
                .build();
    }

    public static Book bookWithId(){
        return Book.builder()
                .id(1L)
                .name("The Lord of the Rings")
                .description("The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1954,07,29))
                .build();
    }

    public static ResponseBookDTO responseBookDTO(){
        return ResponseBookDTO.builder()
                .id(1L)
                .name("The Lord of the Rings")
                .description("The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1954,07,29))
                .build();
    }

    public static ResponseBookDTO bookDTOUpdated(){
        return ResponseBookDTO.builder()
                .id(1L)
                .name("The Lord of the Rings 2")
                .description("The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1954,07,29))
                .build();
    }

    public static RequestBookDTO requestBookDTO(){
        return RequestBookDTO.builder()
                .name("The Lord of the Rings")
                .description("The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien.")
                .authorList(new HashSet<>())
                .publishDate(LocalDate.of(1954,07,29))
                .build();
    }

    public static RegisterBookDTO registerBookDTO(){
        return RegisterBookDTO.builder().id(1L).build();
    }
}
