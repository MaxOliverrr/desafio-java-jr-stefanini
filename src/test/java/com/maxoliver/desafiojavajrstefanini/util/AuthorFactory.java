package com.maxoliver.desafiojavajrstefanini.util;

import com.maxoliver.desafiojavajrstefanini.dtos.RegisterAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.RequestAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.model.entities.Country;
import com.maxoliver.desafiojavajrstefanini.model.entities.enums.Gender;

import java.time.LocalDate;
import java.util.HashSet;

public class AuthorFactory {

    public static final Country SOUTH_AFRICA = Country.builder().name("South Africa").build();
    public static final Country UKRAINE = Country.builder().name("Ukraine").build();
    public static Author authorNoId(){
        return Author.builder()
            .name("J. R. R. Tolkien")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1892, 1, 3))
            .email("tolkien@mail.com")
            .country(SOUTH_AFRICA)
            .cpf("238.192.050-12")
            .bookList(new HashSet<>())
            .build();
    }

    public static Author author2NoId(){
        return Author.builder()
            .name("Clarice Lispector")
            .gender(Gender.FEMALE)
            .birthDate(LocalDate.of(1920,12,10))
            .email("clarice@mail.com")
            .country(UKRAINE)
            .cpf("470.185.170-10")
            .bookList(new HashSet<>())
            .build();
    }

    public static RequestAuthorDTO requestAuthorDTO(){
        return RequestAuthorDTO.builder()
            .name("J. R. R. Tolkien")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1892, 1, 3))
            .email("tolkien@mail.com")
            .country(SOUTH_AFRICA)
            .cpf("238.192.050-12")
            .bookList(new HashSet<>())
            .build();
    }

    public static ResponseAuthorDTO responseAuthorDTO(){
        return ResponseAuthorDTO.builder()
            .id(1L)
            .name("J. R. R. Tolkien")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1892, 1, 3))
            .email("tolkien@mail.com")
            .country(SOUTH_AFRICA)
            .cpf("238.192.050-12")
            .bookList(new HashSet<>())
            .build();
    }

    public static Author authorWithId(){
        return Author.builder()
            .id(1L)
            .name("J. R. R. Tolkien")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1892, 1, 3))
            .email("tolkien@mail.com")
            .country(SOUTH_AFRICA)
            .cpf("238.192.050-12")
            .bookList(new HashSet<>())
            .build();
    }

    public static RequestAuthorDTO authorDTO2Updated(){
        return RequestAuthorDTO.builder()
            .name("Chaya Pinkhasivna Lispector")
            .gender(Gender.FEMALE)
            .birthDate(LocalDate.of(1920,12,10))
            .email("clarice@mail.com")
            .country(UKRAINE)
            .cpf("470.185.170-10")
            .bookList(new HashSet<>())
            .build();
    }

    public static ResponseAuthorDTO responseAuthorDTO2(){
        return ResponseAuthorDTO.builder()
            .id(2L)
            .name("Clarice Lispector")
            .gender(Gender.FEMALE)
            .birthDate(LocalDate.of(1920,12,10))
            .email("clarice@mail.com")
            .country(UKRAINE)
            .cpf("470.185.170-10")
            .bookList(new HashSet<>())
            .build();
    }

    public static RegisterAuthorDTO registerAuthorDTO(){
        return RegisterAuthorDTO.builder()
            .id(1L)
            .build();
    }
}
