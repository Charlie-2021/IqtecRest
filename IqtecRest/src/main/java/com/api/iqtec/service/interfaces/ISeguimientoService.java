package com.api.iqtec.service.interfaces;

import java.util.List;

import com.api.iqtec.modelo.Seguimiento;

public interface ISeguimientoService {
	
	public List<Seguimiento> findAll();
	
	public boolean update(Seguimiento seguimiento);


}
