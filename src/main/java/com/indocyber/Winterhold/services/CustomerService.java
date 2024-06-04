package com.indocyber.Winterhold.services;

import com.indocyber.Winterhold.dtos.customer.*;
import com.indocyber.Winterhold.dtos.category.CategorySearchDto;
import com.indocyber.Winterhold.models.Customer;
import com.indocyber.Winterhold.models.Gender;
import com.indocyber.Winterhold.repositories.CustomerRespository;
import com.indocyber.Winterhold.repositories.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    
    private CustomerRespository customerRespository;
    private LoanRepository loanRepository;

    public CustomerService(CustomerRespository customerRespository, LoanRepository loanRepository) {
        this.customerRespository = customerRespository;
        this.loanRepository = loanRepository;
    }

    public Page<CustomerRowItemDto> getAll(CustomerSearchDto dto) {
        String membership = dto.getMembershipNumber() == null || dto.getMembershipNumber().isBlank() ? null : dto.getMembershipNumber();
        String name = dto.getName() == null || dto.getName().isBlank() ? null : dto.getName();


        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> customerPage = customerRespository.findAll(pageable, membership,name);
        List<CustomerRowItemDto> dtos = customerPage.getContent()
                .stream().map(customer -> CustomerRowItemDto.builder()
                        .membershipNumber(customer.getMembershipNumber())
                        .fullName(customer.getFirstName()+" "+customer.getLastName())
                        .expireDate(customer.getMembershipExpireDate())
                        .build())
                .toList();
        return new PageImpl<>(dtos, pageable, customerPage.getTotalElements());
    }

    public CustomerUpsertResponseDto save(CustomerUpsertRequestDto dto) {

        LocalDate expiredDate = dto.getMembershipExpireDate() == null ? LocalDate.now().plusYears(2) : dto.getMembershipExpireDate();
        Gender gender;
        if (dto.getGender() == "Female"){
            gender = Gender.FEMALE;
        }else {
            gender = Gender.MALE;
        }


        var customer = Customer.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(gender)
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .membershipExpireDate(expiredDate)
                .build();

        customer = customerRespository.save(customer);

        return convertResponseDto(customer);
    }

    private CustomerUpsertResponseDto convertResponseDto(Customer customer) {
        return CustomerUpsertResponseDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .gender(customer.getGender())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    public CustomerDeleteItemDto getItemByIdForDelete(String membershipNumber) {
        Customer customer = findByID(membershipNumber);
        return CustomerDeleteItemDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .fullName(customer.getFirstName()+" "+customer.getLastName())
                .build();
    }

    public CustomerUpsertRequestDto getCutomerById(String membershipNumber) {
        Customer customer = findByID(membershipNumber);
        return convertCustomerUpsertRequestDto(customer);
    }

    private Customer findByID(String membershipNumber) {
        return customerRespository.findById(membershipNumber)
                .orElseThrow(() -> new IllegalArgumentException("Nama customer tidak ditemukan"));
    }

    private CustomerUpsertRequestDto convertCustomerUpsertRequestDto(Customer customer) {
        return CustomerUpsertRequestDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .gender(customer.getGender().getLabel())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    public void deleteById(String membershipNumber) {
        customerRespository.deleteById(membershipNumber);
    }

    public Integer countCustomerInLoan(String membershipNumber) {
        return loanRepository.countCustomersInLoan(membershipNumber);
    }
}
