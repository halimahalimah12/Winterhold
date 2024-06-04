package com.indocyber.Winterhold.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name="Name")
    private String name;
    @Column(name="Floor")
    private Integer floor;
    @Column(name="Isle")
    private String isle;
    @Column(name="Bay")
    private String bay;

}
