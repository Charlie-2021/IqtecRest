package com.api.iqtec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Estado;
import com.api.iqtec.service.interfaces.IEstadoService;

@RestController
@RequestMapping("/Iqtec/estado")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoController {
	
@Autowired IEstadoService estadoService;
	
	@GetMapping("/consultar")
	public ResponseEntity<List<Estado>> obtenerEstados()
	{
		ResponseEntity<List<Estado>> response;
		List<Estado> todos;
		
		todos = estadoService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}

}
