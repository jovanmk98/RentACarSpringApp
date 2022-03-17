package com.example.rentacarapp.model;

import com.example.rentacarapp.model.enumerations.ShoppingCartStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

    private Long id;

    private LocalDateTime dateCreated;

    private User user;

    private Car product;

    private ShoppingCartStatus status;

    private String dateFrom;

    private String dateTo;

    public ShoppingCart(User user) {
        this.id = (long) (Math.random() * 1000);
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.product = product;
        this.status = ShoppingCartStatus.CREATED;
    }
}
