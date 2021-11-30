  package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.repositorio.ClienteRepository;
import com.api.iqtec.service.interfaces.IClienteService;

@Service
public class ClienteService implements IClienteService {

	@Autowired ClienteRepository daoCliente;

	@Override
	public List<Cliente> findAll() {
		
		return  (List<Cliente>) daoCliente.findAll();
	}

	@Override
	public boolean insert(Cliente cliente) {
		
		boolean exito = false;
		
		if(cliente.getIdCliente() == null || !daoCliente.existsById(cliente.getIdCliente()))
		{
			daoCliente.save(cliente);			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Cliente cliente) {

		boolean exito = false;
		
		if(daoCliente.existsById(cliente.getIdCliente()))
		{
			daoCliente.save(cliente);			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;
		
		if(daoCliente.existsById(id))
		{
			daoCliente.deleteById(id);
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Cliente> findByNombre(String razonSocial) {
		return daoCliente.findByRazonSocial(razonSocial);
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		// TODO Auto-generated method stub
		return daoCliente.findById(id);
	}



}
