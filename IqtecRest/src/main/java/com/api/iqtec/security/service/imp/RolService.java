package com.api.iqtec.security.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.iqtec.security.entity.Rol;
import com.api.iqtec.security.enums.RolNombre;
import com.api.iqtec.security.repository.RolRepository;
import com.api.iqtec.security.service.interfaces.IntRolService;

import java.util.Optional;

@Service
@Transactional
public class RolService implements IntRolService {

	@Autowired private RolRepository daoRol;
	
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
