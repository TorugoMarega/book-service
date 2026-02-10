package br.com.erudio.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String author;
    @Column(nullable = false, length = 250)
    private String title;
    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lauchDate;
    @Column(nullable = false)
    private BigDecimal price;

    @Transient
    private String currency;
    @Transient
    private String enviroment;
}