package com.api.iqtec.service;

import java.util.Optional;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.enums.NombreEstado;


public interface IEstadoService {

	public boolean save(Estado estado);
	
	public Optional<Estado> findByNombreEstado (NombreEstado NombreEstado);
}
