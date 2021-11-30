package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.repositorio.SolicitudRepository;
import com.api.iqtec.service.interfaces.ISolicitudService;

@Service
public class SolicitudService implements ISolicitudService {

	
	@Autowired SolicitudRepository daoSolicitud;

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
