package com.indocyber.Winterhold.repositories;

import com.indocyber.Winterhold.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("""
            SELECT a
            FROM Author a
            WHERE (:name IS NULL OR a.firstName LIKE %:name%
                    OR a.lastName LIKE %:name%
                    OR CONCAT(a.firstName,' ',a.lastName) LIKE %:name% )
            """)
    Page<Author> findAll(Pageable pageable, @Param("name") String name);
}
