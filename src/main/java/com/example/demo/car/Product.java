package com.example.demo.car;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity
@Data
@NoArgsConstructor
public class Product implements Comparable<Product>{

    @Id
    @Column
    private UUID product_id;
    @Column(unique = true)
    private String name;
    @Column(name = "price_to_buy")
    private Double priceToBuy;
    @Column
    private String description;
    @Column(name = "price_to_sell")
    private Double priceToSell;

    @Column(name = "number")
    private Integer number;

    @Column
    private String category;

    @Override
    public int compareTo(Product o) {
        return    o.getName().compareTo(this.getName());
    }
}
