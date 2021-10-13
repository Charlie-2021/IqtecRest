package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Transporte;

@Repository
public interface TransporteRepository extends CrudRepository<Transporte, Long> {

	public Optional<Transporte> findByNombre (String nombre);
}
