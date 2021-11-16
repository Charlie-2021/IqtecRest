package com.api.iqtec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;



public interface ISolicitudService {

	public List<Solicitud> findAll ();
	
	public boolean insert(Solicitud solicitud);

	public boolean update(Solicitud solicitud);

	public boolean delete(Long id);
	
	public Optional<Solicitud> findById(Long id);
	
	public Optional<Solicitud> findByReferencia (String referencia);
	
	
} 