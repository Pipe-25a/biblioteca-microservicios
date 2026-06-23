package com.biblioteca.autor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorResponse {
    private Long id;
    
    private String fullName;
}
