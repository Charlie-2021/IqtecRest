package com.api.iqtec.repositorio;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.enums.RolNombre;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
	
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
