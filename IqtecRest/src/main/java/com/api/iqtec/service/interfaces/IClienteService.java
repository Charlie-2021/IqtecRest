package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Cliente;

public interface IClienteService {

	
	public List<Cliente> findAll ();
	
	public boolean insert(Cliente cliente);

	public boolean update(Cliente cliente);

	public boolean delete(Long id);
	
	public Optional<Cliente> findByNombre (String nombre);
}
