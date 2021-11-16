package com.api.iqtec.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.service.interfaces.ISolicitudService;
import com.api.iqtec.service.interfaces.ITipoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/solicitud")
public class SolicitudController {
	
	@Autowired ISolicitudService solicitudService;
	@Autowired ITipoService tipoServices;
	
	@PostMapping("/crear")
	public ResponseEntity<Solicitud> insertarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!solicitudService.insert(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	
	
	@GetMapping ("/consultar")
	public ResponseEntity<List<Solicitud>> obtenerTodasSolicitudes ()
	{
		ResponseEntity<List<Solicitud>> response;
		List<Solicitud> todos;
		
		todos = solicitudService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/actualizar")
	@Transactional
	public ResponseEntity<Solicitud> modificarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!solicitudService.update(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	
	@DeleteMapping ("/eliminar/{id}")
	public ResponseEntity<Long> eliminarSolicitud (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!solicitudService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	@GetMapping("/porReferencia/{referencia}")
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
	

	@PutMapping ("/seguimiento/{id}")
	public ResponseEntity<Solicitud> modificarSolicitud (@PathVariable Long id, @Valid @RequestBody Seguimiento seguimiento)
	{
		Optional<Solicitud> opSolicitud = solicitudService.findById(id);
		Solicitud solicitud = null;
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (opSolicitud.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
		} else {
			solicitud = opSolicitud.get();
			seguimiento.setFecha(LocalDateTime.now());
			solicitud.getSeguimientos().add(seguimiento);
			
			if (!solicitudService.update(solicitud))
				status = HttpStatus.BAD_REQUEST;
			
		}

		return new ResponseEntity<>(solicitud,status);
	}

}
