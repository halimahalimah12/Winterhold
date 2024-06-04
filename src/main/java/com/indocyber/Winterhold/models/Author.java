package com.indocyber.Winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Author")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="Title")
    private String title;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="BirthDate")
    private LocalDate birthDate;
    @Column(name="DeceasedDate")
    private LocalDate deceasedDate;
    @Column(name="Education")
    private String education;
    @Column(name="Summary")
    private String summary;



}
