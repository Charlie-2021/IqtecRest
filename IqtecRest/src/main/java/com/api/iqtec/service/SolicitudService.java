package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.redis.RedisRequestUtility;
import com.api.iqtec.repositorio.SolicitudRepository;
import com.api.iqtec.service.interfaces.ISolicitudService;

@Service
public class SolicitudService implements ISolicitudService {

	
	@Autowired SolicitudRepository daoSolicitud;
	@Autowired RedisRequestUtility redisUtility;
	


	@Override
	public List<Solicitud> findAll() {
		// TODO Auto-generated method stub
		return (List<Solicitud>) daoSolicitud.findAll();
	}

	@Override
	public boolean insert(Solicitud solicitud) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(solicitud.getIdSolicitud() == null || !daoSolicitud.existsById(solicitud.getIdSolicitud()))
		{
			daoSolicitud.save(solicitud);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Solicitud solicitud) {

		boolean exito = false;
		
		if(daoSolicitud.existsById(solicitud.getIdSolicitud()))
		{
			daoSolicitud.save(solicitud);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean delete(Long id) {

		boolean exito = false;
		
		if(daoSolicitud.existsById(id))
		{
			daoSolicitud.deleteById(id);;
			exito = true;
		}
		return exito;
	}

	
	
//	@Override
//	public List<Solicitud> findAll() {
//		
//		List<Solicitud> requests = redisUtility.getValues();
//		
//		if(requests.size() < 1) {
//			requests = (List<Solicitud>) daoSolicitud.findAll();
//			
//			redisUtility.setValues(requests);
//		}
//		
//		return requests;
//	}
//
//	@Override
//	public boolean insert(Solicitud solicitud) {
//		
//		boolean exito = false;
//		
//		if(solicitud.getIdSolicitud() == null || !daoSolicitud.existsById(solicitud.getIdSolicitud()))
//		{
//			daoSolicitud.save(solicitud);
//			
//			redisUtility.updateRedisCache(daoSolicitud.findByReferencia(solicitud.getReferencia()).get(), "insert");
//			
//			exito = true;
//		}
//		return exito;
//	}
//
//	@Override
//	public boolean update(Solicitud solicitud) {
//
//		boolean exito = false;
//		
//		if(daoSolicitud.existsById(solicitud.getIdSolicitud()))
//		{
//			daoSolicitud.save(solicitud);
//			
//			redisUtility.updateRedisCache(solicitud, "update");
//
//			
//			exito = true;
//		}
//		return exito;
//	}
//
//	@Override
//	public boolean delete(Long id) {
//
//		boolean exito = false;
//		
//		if(daoSolicitud.existsById(id))
//		{
//			
//			redisUtility.updateRedisCache(daoSolicitud.findById(id).get(), "delete");
//			
//			daoSolicitud.deleteById(id);;
//			exito = true;
//		}
//		return exito;
//	}

	@Override
	public Optional<Solicitud> findByReferencia(String referencia) {
		// TODO Auto-generated method stub
		return daoSolicitud.findByReferencia(referencia);
	}

	@Override
	public Optional<Solicitud> findById(Long id) {
		// TODO Auto-generated method stub
		return daoSolicitud.findById(id);
	}


}
