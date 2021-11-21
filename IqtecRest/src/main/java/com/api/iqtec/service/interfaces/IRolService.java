package com.api.iqtec.service.interfaces;

import java.util.Optional;

import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.enums.RolNombre;

public interface IRolService {

	public boolean save ( Rol rol);
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre);
}
