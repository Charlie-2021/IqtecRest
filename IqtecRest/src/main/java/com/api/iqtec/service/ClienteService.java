  package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.redis.RedisClientUtility;
import com.api.iqtec.repositorio.ClienteRepository;
import com.api.iqtec.service.interfaces.IClienteService;

@Service
public class ClienteService implements IClienteService {

	@Autowired ClienteRepository daoCliente;
	
	@Autowired RedisClientUtility redisClientUtility;
	
	
	
	@Override
	public List<Cliente> findAll() {
		
		List<Cliente> clients = redisClientUtility.getValues();
		
		if(clients.size() < 1) {
			clients = (List<Cliente>) daoCliente.findAll();
			
			redisClientUtility.setValues(clients);
		}
		return clients;
	}

	@Override
	public boolean insert(Cliente cliente) {
		
		boolean exito = false;
		
		if(cliente.getIdCliente() == null || !daoCliente.existsById(cliente.getIdCliente()))
		{
			daoCliente.save(cliente);
			
			redisClientUtility.updateRedisCache(daoCliente.findByRazonSocial(cliente.getRazonSocial()).get(), "insert");
			
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
			
			redisClientUtility.updateRedisCache(cliente, "update");
			
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		
		boolean exito = false;
		
		if(daoCliente.existsById(id))
		{
			redisClientUtility.updateRedisCache(daoCliente.findById(id).get(), "delete");
			
			daoCliente.deleteById(id);
			
			exito = true;
		}
		return exito;
	}

	@Override
	public Optional<Cliente> findByNombre(String razonSocial) {
		return daoCliente.findByRazonSocial(razonSocial);
	}



}
