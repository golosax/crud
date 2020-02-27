package com.ce.crud.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String image;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;

    private String productId;
    private String name;
    private BigDecimal price;

}
