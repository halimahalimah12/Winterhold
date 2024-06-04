package com.indocyber.Winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "Code")
    private String code;
    @Column(name = "Title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "CategoryName")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "AuthorId")
    private Author author;
    @Column(name = "IsBorrowed")
    private Boolean isBorrowed;
    @Column(name = "Summary")
    private String summary;
    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;
    @Column(name = "TotalPage")
    private Integer totalPage;

}
