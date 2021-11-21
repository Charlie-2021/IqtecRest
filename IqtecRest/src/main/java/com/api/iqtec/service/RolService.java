package com.api.iqtec.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.enums.RolNombre;
import com.api.iqtec.repositorio.RolRepository;
import com.api.iqtec.service.interfaces.IRolService;

import java.util.Optional;

@Service
@Transactional
public class RolService implements IRolService{

	@Autowired 
	RolRepository daoRol;
	

	@Override
	public boolean save(Rol rol) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(rol.getId() == null || !daoRol.existsById(rol.getId()))
		{
			daoRol.save(rol);
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
		// TODO Auto-generated method stub
		return daoRol.findByRolNombre(rolNombre);
	}

 
}
