package com.api.iqtec.repositorio;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Proyecto;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {

	
}
