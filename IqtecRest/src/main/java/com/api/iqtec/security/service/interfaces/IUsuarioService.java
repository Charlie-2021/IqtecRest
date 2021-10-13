package com.api.iqtec.security.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.security.entity.Usuario;


public interface IUsuarioService {

	public List<Usuario> findAll ();
	
	public boolean insert(Usuario usuario);

	public boolean update(Usuario usuario);

	public boolean delete(Long id);
	
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario);
	
	public boolean existsByNombreUsuario(String nombreUsuario);
	
	
}
