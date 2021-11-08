package com.api.iqtec.controller;

import java.util.List;
import java.util.Optional;

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


import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.service.ITransporteService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/transportes")
public class TransporteController {

	
	@Autowired ITransporteService transporteService;
	
	@PostMapping("/crear")
	public ResponseEntity<Transporte> insertarTransporte (@Valid @RequestBody Transporte transporte)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!transporteService.insert(transporte))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(transporte,status);
	}
	
	
	@GetMapping ("/consultar")
	public ResponseEntity<List<Transporte>> allTransporte ()
	{
		ResponseEntity<List<Transporte>> response;
		List<Transporte> todos;
		
		todos = transporteService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/actualizar")
	public ResponseEntity<Transporte> modificarTransporte (@Valid @RequestBody Transporte transporte)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!transporteService.update(transporte))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(transporte,status);
	}
	
	@DeleteMapping ("/eliminar/{id}")
	public ResponseEntity<Long> eliminarTransporte (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!transporteService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Transporte> findByNombre(@PathVariable String nombre)
	{
		
		ResponseEntity<Transporte> response;
		
		Optional<Transporte> op = transporteService.findByNombre(nombre);
		
		if(op.isPresent())
			response = new ResponseEntity<Transporte>(op.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<Transporte>(HttpStatus.NOT_FOUND);
		
		
		return response;
	}
}
