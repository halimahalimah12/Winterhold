package com.indocyber.Winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Loan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "CustomerNumber")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "BookCode")
    private Book book;
    @Column(name = "LoanDate")
    private LocalDate loanDate;
    @Column(name = "DueDate")
    private LocalDate dueDate;
    @Column(name = "ReturnDate")
    private LocalDate returnDate;
    @Column(name = "Note")
    private String note;

}
