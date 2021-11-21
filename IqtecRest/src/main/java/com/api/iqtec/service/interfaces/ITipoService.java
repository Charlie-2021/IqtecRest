package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.modelo.enums.TipoMaterial;

public interface ITipoService {

	public List<Tipo> findAll();	
	
	public boolean save(Tipo tipo);
	
	public Optional<Tipo> findByTipoMaterial (TipoMaterial tipoMaterial);
}
