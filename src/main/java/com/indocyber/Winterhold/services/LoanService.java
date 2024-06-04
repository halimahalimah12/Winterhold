package com.indocyber.Winterhold.services;

import com.indocyber.Winterhold.dtos.customer.CustomerUpsertRequestDto;
import com.indocyber.Winterhold.dtos.customer.CustomerUpsertResponseDto;
import com.indocyber.Winterhold.dtos.loan.*;
import com.indocyber.Winterhold.dtos.utility.SelectListBookDto;
import com.indocyber.Winterhold.dtos.utility.SelectListCustomerDto;
import com.indocyber.Winterhold.models.Book;
import com.indocyber.Winterhold.models.Customer;
import com.indocyber.Winterhold.models.Gender;
import com.indocyber.Winterhold.models.Loan;
import com.indocyber.Winterhold.repositories.BookRepository;
import com.indocyber.Winterhold.repositories.CustomerRespository;
import com.indocyber.Winterhold.repositories.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    private LoanRepository loanRepository;
    private CustomerRespository customerRespository;
    private BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, CustomerRespository customerRespository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.customerRespository = customerRespository;
        this.bookRepository = bookRepository;
    }

    public Page<LoanRowItemDto> getAll(LoanSearchDto dto) {
        String titleBook = dto.getTitleBook() == null || dto.getTitleBook().isBlank() ? null : dto.getTitleBook();
        String customerName = dto.getCustomerName() == null || dto.getCustomerName().isBlank() ? null : dto.getCustomerName();

        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Loan> loanPage = loanRepository.findAll(pageable, titleBook, customerName);
        List<LoanRowItemDto> dtos = loanPage.getContent()
                .stream().map(loan -> {
                            String isLateStatus;
                            if (loan.getReturnDate() != null && loan.getDueDate() != null) {
                                isLateStatus = loan.getReturnDate().isAfter(loan.getDueDate()) ? "Terlambat" : "Tidak Terlambat";
                            } else {
                                isLateStatus = "-";
                            }
                            return  LoanRowItemDto.builder()
                            .id(loan.getId())
                            .titleBook(loan.getBook().getTitle())
                            .customerName(loan.getCustomer().getFirstName() + " " + loan.getCustomer().getLastName())
                            .loanDate(loan.getLoanDate())
                            .dueDate(loan.getDueDate())
                            .returnDate(loan.getReturnDate())
                            .isLate(isLateStatus)
                            .build();
                        })
                .toList();
        return new PageImpl<>(dtos, pageable, loanPage.getTotalElements());
    }

    public LoanUpsertResponseDto save(LoanUpsertRequestDto dto) {

        LocalDate dueDate = dto.getLoanDate().plusDays(5) ;
        Customer customer = findCustomerById(dto.getCustomerId());
        Book book = findBookById(dto.getBookId());

        var loan = Loan.builder()
                .id(dto.getId())
                .customer(customer)
                .book(book)
                .loanDate(dto.getLoanDate())
                .dueDate(dueDate)
                .note(dto.getNote())
                .build();

        loan = loanRepository.save(loan);

        book.setIsBorrowed(true);
        bookRepository.save(book);

        return convertResponseDto(loan);
    }
    private Book findBookById(String codeBook) {
        return bookRepository.findById(codeBook)
                .orElseThrow(() -> new IllegalArgumentException("Book tidak ditemukan"));
    }
    private Customer findCustomerById(String mambership) {
        return   customerRespository.findById(mambership)
                .orElseThrow(() -> new IllegalArgumentException("Customer tidak ditemukan"));
    }

    private LoanUpsertResponseDto convertResponseDto(Loan loan) {
        return LoanUpsertResponseDto.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getMembershipNumber())
                .bookId(loan.getBook().getCode())
                .loanDate(loan.getLoanDate())
                .note(loan.getNote())
                .build();
    }


    public LoanUpsertRequestDto getLoanById(Integer id) {
        Loan loan = findById(id);
        return convertRequestDto(loan);
    }

    private LoanUpsertRequestDto convertRequestDto(Loan loan) {
        return LoanUpsertRequestDto.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getMembershipNumber())
                .bookId(loan.getBook().getCode())
                .loanDate(loan.getLoanDate())
                .note(loan.getNote())
                .build();               
    }

    private Loan findById(Integer id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("loan tidak ditemukan"));
    }

    public List<SelectListCustomerDto> getCustomerDropdown() {
        List<Customer> customerList = customerRespository.findAll();
        List<Customer> customerAvailable= new ArrayList<>();
        for (Customer customer : customerList) {
            LocalDate expireDate = customer.getMembershipExpireDate();
            if (LocalDate.now().isBefore(expireDate)) {
                customerAvailable.add(customer);
            }
        }

        return customerAvailable.stream()
                .map(customer -> SelectListCustomerDto.builder()
                        .value(customer.getMembershipNumber())
                        .text(customer.getFirstName()+" "+customer.getLastName())
                        .build())
                .toList();
    }
    public List<SelectListBookDto> getBookDropdown() {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookAvailable = new ArrayList<>();
        for (Book book : bookList ){
            if (book.getIsBorrowed() == false) {
                bookAvailable.add(book);
            }
        }

        return bookAvailable.stream()
                .map(book -> SelectListBookDto.builder()
                        .value(book.getCode())
                        .text(book.getTitle())
                        .build())
                .toList() ;
    }


    public LoanDetailItemDto getById(Integer id) {
        Loan loan = findById(id);
        Book book = findBookById(loan.getBook().getCode());
        Customer customer = findCustomerById(loan.getCustomer().getMembershipNumber());

        return LoanDetailItemDto.builder()
                .titleBook(book.getTitle())
                .category(book.getCategory().getName())
                .nameAuthor(book.getAuthor().getTitle()+" "+book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName())
                .floor(book.getCategory().getFloor())
                .isle(book.getCategory().getIsle())
                .bay(book.getCategory().getBay())
                .membershipNumber(customer.getMembershipNumber())
                .nameCustomer(customer.getFirstName()+" "+ customer.getLastName())
                .phone(customer.getPhone())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();

    }

    public void returnBook(Integer id) {
        Loan loan = findById(id);
        Book book = findBookById(loan.getBook().getCode());

        book.setIsBorrowed(false);
        bookRepository.save(book);

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }
}
