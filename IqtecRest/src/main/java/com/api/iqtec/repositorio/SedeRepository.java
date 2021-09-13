package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Sede;

@Repository
public interface SedeRepository extends CrudRepository<Sede, Long> {

	public Optional<Sede> findByNombre (String nombre);
}
