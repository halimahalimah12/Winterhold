package com.indocyber.Winterhold.services;

import com.indocyber.Winterhold.dtos.book.BookRowItemDto;
import com.indocyber.Winterhold.dtos.book.BookSearchDto;
import com.indocyber.Winterhold.dtos.book.BookUpsertRequestDto;
import com.indocyber.Winterhold.dtos.book.BookUpsertResponseDto;
import com.indocyber.Winterhold.dtos.category.CategoryUpsertRequestDto;
import com.indocyber.Winterhold.dtos.category.CategoryUpsertResponseDto;
import com.indocyber.Winterhold.dtos.utility.SelectListAuthorDto;
import com.indocyber.Winterhold.models.Author;
import com.indocyber.Winterhold.models.Book;
import com.indocyber.Winterhold.models.Category;
import com.indocyber.Winterhold.repositories.AuthorRepository;
import com.indocyber.Winterhold.repositories.BookRepository;
import com.indocyber.Winterhold.repositories.CategoryRepository;
import com.indocyber.Winterhold.repositories.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private LoanRepository loanRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.loanRepository = loanRepository;
    }

    public Page<BookRowItemDto> getAllBook(String name, BookSearchDto dto) {
        String title = dto.getTitle() == null || dto.getTitle().isBlank() ? null : dto.getTitle();
        String author = dto.getAuthor() == null || dto.getAuthor().isBlank() ? null : dto.getAuthor();
        Boolean isBorrowed = dto.getIsBorrowed() == null ? false : dto.getIsBorrowed();

        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Book> bookPage = bookRepository.getBooksByCategoryName(pageable, name, title, author, isBorrowed);

        List<BookRowItemDto> dtos = bookPage.getContent()
                .stream().map(book -> BookRowItemDto.builder()
                        .code(book.getCode())
                        .title(book.getTitle())
                        .author(book.getAuthor().getTitle() + ". " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName())
                        .isBorrowed(book.getIsBorrowed() == false ? "Available" : "Borrowed")
                        .releaseDate(book.getReleaseDate())
                        .totalPage(book.getTotalPage())
                        .build())
                .toList();
        return new PageImpl<>(dtos, pageable, bookPage.getTotalElements());
    }

    public List<SelectListAuthorDto> getAuthorDropdown() {
        return authorRepository.findAll().stream()
                .map(author -> SelectListAuthorDto.builder()
                        .value(author.getId())
                        .text(author.getTitle() + " " + author.getFirstName() + " " + author.getLastName())
                        .build())
                .toList();
    }

    public BookUpsertResponseDto save(BookUpsertRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("ID tidak ditemukan"));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("ID todak ditemukan"));

        var book = Book.builder()
                .code(dto.getCode())
                .title(dto.getTitle())
                .category(category)
                .author(author)
                .isBorrowed(dto.getIsBorrowed() == null ? false : dto.getIsBorrowed())
                .releaseDate(dto.getReleaseDate())
                .totalPage(dto.getTotalPage())
                .summary(dto.getSummary())
                .build();

        book = bookRepository.save(book);

        return convertResponseDto(book);
    }

    private BookUpsertResponseDto convertResponseDto(Book book) {
        return BookUpsertResponseDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .categoryId(book.getCategory().getName())
                .authorId(book.getAuthor().getTitle() + " " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName())
                .isBorrowed(book.getIsBorrowed())
                .releaseDate(book.getReleaseDate())
                .totalPage(book.getTotalPage())
                .summary(book.getSummary())
                .build();
    }

    public BookUpsertRequestDto getById(String codeBook) {
        Book book = cekBookFindByID(codeBook);
        return convertBookUpsertRequestDto(book);
    }

    private Book cekBookFindByID(String name) {
        return bookRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Judul buku tidak ditemukan"));
    }

    private BookUpsertRequestDto convertBookUpsertRequestDto(Book book) {
        return BookUpsertRequestDto.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .categoryId(book.getCategory().getName())
                .authorId(book.getAuthor().getId())
                .releaseDate(book.getReleaseDate())
                .totalPage(book.getTotalPage())
                .summary(book.getSummary())
                .build();
    }

    public Integer countBookInLoan(String codeBook) {
        return loanRepository.countBookInLoad(codeBook);
    }

    public void deleteById(String codeBook) {
        bookRepository.deleteById(codeBook);
    }

}
