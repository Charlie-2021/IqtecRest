package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.Seguimiento;

public interface SeguimientoRepository extends CrudRepository<Seguimiento, Long> {

	public Optional<Seguimiento> findByEstado( Estado estado);
}
