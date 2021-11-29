package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.enums.NombreEstado;


public interface IEstadoService {

	public boolean save(Estado estado);
	
	public Optional<Estado> getByNombreEstado (NombreEstado NombreEstado);
	
	public List<Estado> findAll();
}
