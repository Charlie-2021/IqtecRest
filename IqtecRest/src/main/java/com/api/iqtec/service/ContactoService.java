package com.api.iqtec.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Contacto;
import com.api.iqtec.repositorio.ContactoRepository;
import com.api.iqtec.service.interfaces.IContactoService;

@Service
public class ContactoService implements IContactoService {
	
	@Autowired private ContactoRepository daoContacto;

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(daoContacto.existsById(id))
		{
			daoContacto.deleteById(id);
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Contacto> findById(Long idContacto) {
		// TODO Auto-generated method stub
		return daoContacto.findById(idContacto);
	}
	
	@Override
	public boolean update(Contacto contacto) {

		boolean exito = false;
		
		if(daoContacto.existsById(contacto.getIdContacto()))
		{
			daoContacto.save(contacto);			
			exito = true;
		}
		return exito;
	}

}
