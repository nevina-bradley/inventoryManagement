package com.codedifferently.inventorymanagement.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class itemTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String itemName;

    @NonNull
    private Integer unitsPerItem;

    @NonNull
    private boolean loanable;

    @NonNull
    private double notificationLimit;

    private List<String> itemProperties;
}
