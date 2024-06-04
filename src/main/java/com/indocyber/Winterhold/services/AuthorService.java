package com.indocyber.Winterhold.services;

import com.indocyber.Winterhold.dtos.author.*;
import com.indocyber.Winterhold.models.Author;
import com.indocyber.Winterhold.models.Book;
import com.indocyber.Winterhold.repositories.AuthorRepository;
import com.indocyber.Winterhold.repositories.BookRepository;
import com.indocyber.Winterhold.restAPI.dto.RestAuthorRowItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Page<AuthorRowItemDto> getAll(AuthorSearchDto dto) {
        String name = dto.getName() == null || dto.getName().isBlank() ? null : dto.getName();

        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Author> authorPage = authorRepository.findAll(pageable, name);
        List<AuthorRowItemDto> dtos = authorPage.getContent()
                .stream().map(author -> AuthorRowItemDto.builder()
                        .id(author.getId())
                        .name(author.getTitle() + ". " + author.getFirstName() + " " + author.getLastName())
                        .age(getAge(author.getBirthDate(), author.getDeceasedDate()))
                        .status(getStatus(author.getDeceasedDate()))
                        .education(author.getEducation().isBlank() ? "No Information" : author.getEducation())
                        .build())
                .toList();
        return new PageImpl<>(dtos, pageable, authorPage.getTotalElements());
    }

    private Integer getAge(LocalDate brithdate, LocalDate deceasedDate) {

        if (deceasedDate == null) {
            Integer today = LocalDate.now().getYear();
            return today - brithdate.getYear();
        } else {
            return deceasedDate.getYear() - brithdate.getYear();
        }

    }

    private String getStatus(LocalDate deceasedDate) {
        if (deceasedDate == null) {
            return "Alive";
        } else {
            return "Deceased";
        }
    }

    public AuthorUpsertResponseDto save(AuthorUpsertRequestDto dto) {
        var author = Author.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .deceasedDate(dto.getDeceaseDate())
                .education(dto.getEducation())
                .summary(dto.getSummary())
                .build();
        author = authorRepository.save(author);

        return convertResponseDto(author);
    }

    private AuthorUpsertResponseDto convertResponseDto(Author author) {
        return AuthorUpsertResponseDto.builder()
                .id(author.getId())
                .title(author.getTitle())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceaseDate(author.getDeceasedDate())
                .education(author.getEducation())
                .summary(author.getSummary())
                .build();
    }

    public AuthorUpsertRequestDto getById(Integer id) {
        Author author = findByID(id);
        return convertAuthorUpsertRequestDto(author);
    }

    public AuthorDeleteItemDto getItemByIdForDelete(Integer id) {
        Author author = findByID(id);
        return AuthorDeleteItemDto.builder()
                .id(author.getId())
                .fullName(author.getTitle() + " " + author.getFirstName() + " " + author.getLastName())
                .build();
    }

    private Author findByID(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID tidak ditemukan"));
    }

    private AuthorUpsertRequestDto convertAuthorUpsertRequestDto(Author author) {
        return AuthorUpsertRequestDto.builder()
                .id(author.getId())
                .title(author.getTitle())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceaseDate(author.getDeceasedDate())
                .education(author.getEducation())
                .summary(author.getSummary())
                .build();
    }

    public Integer countBookByAuthor(Integer id) {
        return bookRepository.countBookInAuthor(id);
    }

    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }

    public List<AuthorsBookRowDto> authorsBook(Integer authorId) {
        List<Book> books = bookRepository.getBooksByAuthorId(authorId);

        List<AuthorsBookRowDto> authorsBookList = books.stream().map(
                        book -> AuthorsBookRowDto.builder()
                                .title(book.getTitle())
                                .Category(book.getCategory().getName())
                                .isBorrowed(book.getIsBorrowed() == false ? "Available" : "Borrowed")
                                .releaseDate(book.getReleaseDate())
                                .totalPage(book.getTotalPage())
                                .build())
                .toList();
        return authorsBookList;
    }

    //    METHOD UNTUK REST API
    public Page<RestAuthorRowItemDto> getAllRest(AuthorSearchDto dto) {
        String name = dto.getName() == null || dto.getName().isBlank() ? null : dto.getName();

        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Author> authorPage = authorRepository.findAll(pageable, name);
        List<RestAuthorRowItemDto> dtos = authorPage.getContent()
                .stream().map(author -> convertRestRowItemDto(author))
                .toList();
        return new PageImpl<>(dtos, pageable, authorPage.getTotalElements());
    }

    public RestAuthorRowItemDto getByIdRest(Integer id) {
        Author author = findByID(id);
        return convertRestRowItemDto(author);
    }

    private RestAuthorRowItemDto convertRestRowItemDto(Author author) {
        return RestAuthorRowItemDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceasedDate(author.getDeceasedDate())
                .education(author.getEducation().isBlank() ? "No Information" : author.getEducation())
                .summary(author.getSummary())
                .build();
    }

    public RestAuthorRowItemDto insert(AuthorUpsertRequestDto dto) {
        var author = Author.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .deceasedDate(dto.getDeceaseDate())
                .education(dto.getEducation())
                .summary(dto.getSummary())
                .build();
        author = authorRepository.save(author);

        return convertRestRowItemDto(author);
    }

    public RestAuthorRowItemDto delete(Integer id) {
        Author author = findByID(id);
        authorRepository.deleteById(id);
        return convertRestRowItemDto(author);
    }







}
