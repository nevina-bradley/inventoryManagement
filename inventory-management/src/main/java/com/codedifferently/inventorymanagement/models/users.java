package com.codedifferently.inventorymanagement.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Enum role;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String token;

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s", id, role, email, password, token);
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
