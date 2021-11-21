package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Usuario;


public interface IUsuarioService {

	public List<Usuario> findAll ();
	
	public boolean save(Usuario usuario);

	public boolean update(Usuario usuario);

	public boolean delete(Long id);
	
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario);
	
	public boolean existsByNombreUsuario(String nombreUsuario);
	
	
}
