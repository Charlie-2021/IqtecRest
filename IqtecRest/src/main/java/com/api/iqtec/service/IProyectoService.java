package com.api.iqtec.service;

import java.util.List;

import com.api.iqtec.modelo.Proyecto;

public interface IProyectoService {

	public List<Proyecto> findAll ();
	
	public boolean insert(Proyecto proyecto);

	public boolean update(Proyecto proyecto);

	public boolean delete(Long id);
}
