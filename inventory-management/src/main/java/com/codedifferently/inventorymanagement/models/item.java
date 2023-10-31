package com.codedifferently.inventorymanagement.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Integer unitsPerItem;

    @NonNull
    private Date purchaseDate;

    private Date lendingStart;

    private Date lendingEnd;

    private String fundingFrom;

    private String assetNumber;

    private String serialNumber;

    private loanee loanee;

    private Map<Integer, String> itemProperties;

    @Override
    public String toString() {
        return String.format("%d %d %s %s %s %s %s %s %s %s", id, unitsPerItem, purchaseDate, lendingStart, lendingEnd, fundingFrom, assetNumber, serialNumber, loanee, itemProperties);
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
