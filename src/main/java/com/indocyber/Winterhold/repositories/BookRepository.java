package com.indocyber.Winterhold.repositories;

import com.indocyber.Winterhold.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {

    @Query("""
            SELECT b
            FROM Book b
            WHERE b.author.id = :authorId
            """)
    List<Book> getBooksByAuthorId (@Param("authorId") Integer authorId);

    @Query("""
            SELECT COUNT(*)
            FROM Book b
            WHERE b.author.id = :authorId
            """)
    Integer countBookInAuthor (@Param("authorId") Integer authorId);

    @Query("""
            SELECT COUNT(*)
            FROM Book b
            WHERE b.category.name = :name
            """)
    Integer countBookInCategory (@Param("name") String name);

    @Query("""
            SELECT b
            FROM Book b
            WHERE (b.category.name = :name)
            AND (:title IS NULL OR b.title LIKE %:title%)
            AND (:author IS NULL OR
                b.author.firstName LIKE %:author% OR
                b.author.lastName LIKE %:author% OR
                CONCAT(b.author.firstName,' ',b.author.lastName) LIKE %:author%)
            And (b.isBorrowed = :isBorrowed)

            """)

    Page<Book> getBooksByCategoryName (Pageable pageable,
                                       @Param("name") String name,
                                        @Param("title") String title,
                                       @Param("author") String author,
                                       @Param("isBorrowed") Boolean isBorrowed);
}
