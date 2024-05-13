package com.jovisco.spring6reactive.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
