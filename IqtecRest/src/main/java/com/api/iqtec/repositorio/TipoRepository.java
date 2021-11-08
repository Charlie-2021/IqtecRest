package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.modelo.enums.TipoMaterial;

@Repository
public interface TipoRepository extends CrudRepository<Tipo, Long> {

	Optional<Tipo> findByTipoMaterial (TipoMaterial tipoMaterial);
}
