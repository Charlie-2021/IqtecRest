package com.api.iqtec.security.entity;


import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.api.iqtec.security.enums.RolNombre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ROL")
public class Rol {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;


}
