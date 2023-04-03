package com.maxoliver.desafiojavajrstefanini.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxoliver.desafiojavajrstefanini.model.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Embedded
    @Column(nullable = false)
    private Country country;

    @Column(unique = true)
    private String cpf;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Authors_Books",joinColumns = @JoinColumn(name = "authorId"), inverseJoinColumns = @JoinColumn(name = "bookId"))
    private Set<Book> bookList = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
