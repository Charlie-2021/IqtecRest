package com.api.iqtec.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NuevoUsuario {
	
    @NotBlank
    @EqualsAndHashCode.Include
    private String nombreUsuario;

    @NotBlank
    private String password;
    
    private Set<String> roles = new HashSet<>();

  
}
