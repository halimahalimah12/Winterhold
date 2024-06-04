package com.indocyber.Winterhold.repositories;

import com.indocyber.Winterhold.models.Author;
import com.indocyber.Winterhold.models.Book;
import com.indocyber.Winterhold.models.Category;
import com.indocyber.Winterhold.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRespository extends JpaRepository<Customer,String> {
    @Query("""
            SELECT c
            FROM Customer c
            WHERE (:name IS NULL OR c.firstName LIKE %:name%
                    OR c.lastName LIKE %:name%
                    OR CONCAT(c.firstName,' ',c.lastName) LIKE %:name% )
            AND (:membership IS NULL OR c.membershipNumber LIKE %:membership%)
            """)
    Page<Customer> findAll(Pageable pageable,
                           @Param("membership" ) String membership,
                           @Param("name") String name);

}
