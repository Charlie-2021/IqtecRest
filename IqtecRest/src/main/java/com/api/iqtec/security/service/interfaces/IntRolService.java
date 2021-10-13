package com.api.iqtec.security.service.interfaces;

import java.util.Optional;

import com.api.iqtec.security.entity.Rol;
import com.api.iqtec.security.enums.RolNombre;

public interface IntRolService {

	public boolean save ( Rol rol);
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre);
}
