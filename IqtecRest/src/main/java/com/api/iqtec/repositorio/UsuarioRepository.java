package com.api.iqtec.repositorio;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    boolean existsByNombreUsuario(String nombreUsuario);
    

}
