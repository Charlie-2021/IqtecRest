package com.api.iqtec.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.modelo.enums.TipoMaterial;
import com.api.iqtec.repositorio.TipoRepository;
import com.api.iqtec.service.interfaces.ITipoService;

@Service
public class TipoService implements ITipoService {

	
	@Autowired
	private TipoRepository daoTipo;
	
	
	@Override
	public boolean save(Tipo tipo) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(tipo.getIdTipo() == null || !daoTipo.existsById(tipo.getIdTipo())) 
		{
			daoTipo.save(tipo);
			exito = true;
		}
			
		
		return exito;
	}

	@Override
	public Optional<Tipo> findByTipoMaterial(TipoMaterial tipoMaterial) {
		// TODO Auto-generated method stub
		return daoTipo.findByTipoMaterial(tipoMaterial);
	}

}
