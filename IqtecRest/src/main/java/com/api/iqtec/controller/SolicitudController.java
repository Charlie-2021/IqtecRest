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
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.service.ISolicitudService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec")
public class SolicitudController {
	
	@Autowired ISolicitudService solicitudService;
	
	@PostMapping("/solicitud/crear")
	public ResponseEntity<Solicitud> insertarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!solicitudService.insert(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	
	
	@GetMapping ("/solicitud/consultar")
	public ResponseEntity<List<Solicitud>> obtenerTodasSolicitudes ()
	{
		ResponseEntity<List<Solicitud>> response;
		List<Solicitud> todos;
		
		todos = solicitudService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/solicitud/actualizar")
	public ResponseEntity<Solicitud> modificarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!solicitudService.update(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	
	@DeleteMapping ("/solicitud/eliminar/{id}")
	public ResponseEntity<Long> eliminarSolicitud (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!solicitudService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	@GetMapping("/solicitud/porReferencia/{referencia}")
	public ResponseEntity<Solicitud> findByReferencia(@PathVariable String referencia)
	{
		
		ResponseEntity<Solicitud> response;
		
		Optional<Solicitud> op = solicitudService.findByReferencia(referencia);
		
		if(op.isPresent())
			response = new ResponseEntity<Solicitud>(op.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<Solicitud>(HttpStatus.NOT_FOUND);
		
		
		return response;
	}

}
