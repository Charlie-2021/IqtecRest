package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.api.iqtec.modelo.Solicitud;

public interface SolicitudRepository extends CrudRepository<Solicitud, Long> {

	public Optional<Solicitud> findByReferencia (String referencia);
}
