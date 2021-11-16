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

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Sede;
import com.api.iqtec.service.interfaces.ISedeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/sedes")
public class SedeController {

	@Autowired ISedeService sedeService;
	
	@PostMapping("/crear")
	public ResponseEntity<Sede> insertarSede (@Valid @RequestBody Sede sede)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!sedeService.insert(sede))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(sede,status);
	}
	
	
	@GetMapping ("/consultar")
	public ResponseEntity<List<Sede>> obtenerSedes ()
	{
		ResponseEntity<List<Sede>> response;
		List<Sede> todos;
		
		todos = sedeService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/actualizar")
	public ResponseEntity<Sede> modificarSede (@Valid @RequestBody Sede sede)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!sedeService.update(sede))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(sede,status);
	}
	
	@DeleteMapping ("/eliminar/{id}")
	public ResponseEntity<Long> eliminarSede (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!sedeService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Sede> findByNombre(@PathVariable String nombre)
	{
		
		ResponseEntity<Sede> response;
		
		Optional<Sede> op = sedeService.findByNombre(nombre);
		
		if(op.isPresent())
			response = new ResponseEntity<Sede>(op.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<Sede>(HttpStatus.NOT_FOUND);
		
		
		return response;
	}
}
