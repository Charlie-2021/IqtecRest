package com.api.iqtec.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.redis.RedisUserUtility;
import com.api.iqtec.repositorio.UsuarioRepository;
import com.api.iqtec.service.interfaces.IUsuarioService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService{

    @Autowired 
    UsuarioRepository daoUsuario;
    @Autowired RedisUserUtility redisUtility;

    @Override
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
		// TODO Auto-generated method stub
		return daoUsuario.findByNombreUsuario(nombreUsuario);
	}

    @Override
	public boolean existsByNombreUsuario(String nombreUsuario) {
		// TODO Auto-generated method stub
		return daoUsuario.existsByNombreUsuario(nombreUsuario);
	}
    
	
    @Override
	public boolean save( Usuario usuario) {
    	
    	boolean exito = false;
		
		if(usuario.getId() == null ||!daoUsuario.existsById(usuario.getId()))
		{
			daoUsuario.save(usuario);
			
			redisUtility.updateRedisCache(daoUsuario.findByNombreUsuario(usuario.getNombreUsuario()).get(), "insert");
			
			exito = true;
		}
		return exito;
    }
    
	
    @Override
	public List<Usuario> findAll() {
		
    	List<Usuario> transports = redisUtility.getValues();
		
		if(transports.size() < 1) {
			transports = (List<Usuario>) daoUsuario.findAll();
			
			redisUtility.setValues(transports);
		}
		
		return transports;
	}


    @Override
	public boolean update(Usuario usuario) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(daoUsuario.existsById(usuario.getId()))
		{
			daoUsuario.save(usuario);
			
			redisUtility.updateRedisCache(usuario, "update");

			
			exito = true;
		}
		return exito;
	}

    @Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(daoUsuario.existsById(id))
		{
			redisUtility.updateRedisCache(daoUsuario.findById(id).get(), "delete");

			
			daoUsuario.deleteById(id);
			exito = true;
		}
		return exito;
	}


}
