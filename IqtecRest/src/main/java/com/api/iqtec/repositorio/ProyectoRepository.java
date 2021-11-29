package com.api.iqtec.repositorio;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Proyecto;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {

	public Optional<Proyecto> findByNombre (String nombre);
	
}
