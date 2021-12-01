package com.api.iqtec.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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

@Entity
@Table(name = "USUARIOS")
public class Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_USUARIO")
	@ApiModelProperty(hidden = true)
    private Long id;

    
    @NotNull
    @Column(unique = true)
	@ApiModelProperty(name = "Nombre",notes = "Nombre del usuario",required = true, position = 0 )
    private String nombreUsuario;

    
    @NotNull
    @ApiModelProperty(name = "Password",notes = "Indica el password",required = true, position = 1 )
    private String password;
    
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USUARIO_ROL", joinColumns = @JoinColumn(name = "ID_USUARIO"),
    inverseJoinColumns = @JoinColumn(name = "ID_ROL"))
    private Set<Rol> roles = new HashSet<>();


    
    public Usuario(@NotNull String nombreUsuario, @NotNull String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
    

    @Column(name = "ACTIVO")
    @ApiModelProperty(hidden = true)
    private boolean activo;
}
