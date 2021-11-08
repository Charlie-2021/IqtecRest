package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.enums.NombreEstado;


@Repository
public interface EstadoRepository extends CrudRepository<Estado, Long> {

	Optional<Estado> findByNombreEstado (NombreEstado nombreEstado);
}
