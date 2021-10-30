package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Solicitud;

@Repository
public interface SolicitudRepository extends CrudRepository<Solicitud, Long> {

	public Optional<Solicitud> findByReferencia (String referencia);
}
