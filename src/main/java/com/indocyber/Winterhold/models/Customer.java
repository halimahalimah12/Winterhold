package com.indocyber.Winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name="MembershipNumber")
    private String membershipNumber;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="BirthDate")
    private LocalDate birthDate;
    @Column(name="Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="Phone")
    private String phone;
    @Column(name="Address")
    private String address;
    @Column(name="MembershipExpireDate")
    private LocalDate membershipExpireDate;

}
