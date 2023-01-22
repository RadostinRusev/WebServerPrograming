package com.example.demo.car;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDTO {

    private UUID carid;

    private String name;

    private String description;

    private Double priceToBuy;

    private Double priceToSell;

    private Integer number;

    private String category;

}
