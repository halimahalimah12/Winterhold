package com.indocyber.Winterhold.repositories;

import com.indocyber.Winterhold.models.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan,Integer> {

    @Query("""
            SELECT COUNT(*)
            FROM Loan l
            WHERE l.book.code = :codeBook
            """)
    Integer countBookInLoad(@Param("codeBook") String codeBook);

    @Query("""
            SELECT COUNT(*)
            FROM Loan l
            WHERE l.customer.membershipNumber = :membershipNumber
            """)
    Integer countCustomersInLoan(@Param("membershipNumber") String membershipNumber);

    @Query("""
            SELECT l
            FROM Loan l
            WHERE (:titleBook IS NULL OR l.book.title LIKE %:titleBook%)
            AND (:customerName IS NULL OR l.customer.firstName LIKE %:customerName%
                OR l.customer.lastName LIKE %:customerName%
                OR CONCAT(l.customer.firstName,' ',l.customer.lastName) LIKE %:customerName%) 
            """)
    Page<Loan> findAll(Pageable pageable,
                       @Param("titleBook") String titleBook,
                       @Param("customerName") String customerName);
}
