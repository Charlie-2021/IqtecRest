package com.api.iqtec.service.interfaces;

import java.util.Optional;

import com.api.iqtec.modelo.Contacto;

public interface IContactoService {

	public boolean delete(Long id);
	
	public Optional<Contacto> findById (Long idContacto);
}
