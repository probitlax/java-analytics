package com.example.analytics.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROFILE")
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PRF_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "BUDGET")
    private BigDecimal budget;

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String name, String surname, BigDecimal budget) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.budget = budget;
    }
}
