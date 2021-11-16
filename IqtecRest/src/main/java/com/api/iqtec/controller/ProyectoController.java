package com.api.iqtec.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.service.interfaces.IProyectoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/proyectos")
public class ProyectoController {

	
	@Autowired IProyectoService proyectoService;
	
	@PostMapping("/crear")
	public ResponseEntity<Proyecto> insertarProyecto (@Valid @RequestBody Proyecto proyecto)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!proyectoService.insert(proyecto))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(proyecto,status);
	}
	
	
	@GetMapping ("/consultar")
	public ResponseEntity<List<Proyecto>> obtenerTodosProyectos ()
	{
		ResponseEntity<List<Proyecto>> response;
		List<Proyecto> todos;
		
		todos = proyectoService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/actualizar")
	public ResponseEntity<Proyecto> modificarProyecto (@Valid @RequestBody Proyecto proyecto)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!proyectoService.update(proyecto))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(proyecto,status);
	}
	
	@DeleteMapping ("/eliminar/{id}")
	public ResponseEntity<Long> eliminarProyecto (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!proyectoService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
}
