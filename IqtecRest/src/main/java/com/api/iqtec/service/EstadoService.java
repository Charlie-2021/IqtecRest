package com.api.iqtec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.enums.NombreEstado;
import com.api.iqtec.repositorio.EstadoRepository;
import com.api.iqtec.service.interfaces.IEstadoService;

@Service
public class EstadoService implements IEstadoService {

	@Autowired EstadoRepository daoEstado;
	@Override
	public boolean save(Estado estado) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(estado.getIdEstado() == null || !daoEstado.existsById(estado.getIdEstado())) 
		{
			daoEstado.save(estado);
			exito = true;
		}


		return exito;
	}

	@Override
	public Optional<Estado> getByNombreEstado(NombreEstado nombreEstado) {
		// TODO Auto-generated method stub
		return daoEstado.findByNombreEstado(nombreEstado);
	}

	@Override
	public List<Estado> findAll() {
		// TODO Auto-generated method stub
		return (List<Estado>) daoEstado.findAll();
	}

}
