package com.api.iqtec.security.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.security.entity.Rol;
import com.api.iqtec.security.enums.RolNombre;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
	
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
