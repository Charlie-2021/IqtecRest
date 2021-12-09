package com.api.iqtec.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Sede;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.service.interfaces.ISolicitudService;
import com.api.iqtec.service.interfaces.ITransporteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transportes")
public class TransporteController {

	
	@Autowired ITransporteService transporteService;
	@Autowired ISolicitudService solicitudService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	@ApiOperation(value = "Crear transporte", notes = "Agregar un nuevo transporte a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El transporte fue insertado correctamente.", response = Transporte.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Transporte> insertarTransporte (@Valid @RequestBody Transporte transporte)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		Optional<Transporte> op = transporteService.findByNombre(transporte.getNombre());
		
		if (op.isPresent()) {
			Transporte transNoActivo = op.get();
			transporte.setActivo(true);
			transporte.setId(transNoActivo.getId());
			transporteService.update(transporte);
			
		} else {
			transporte.setActivo(true);
			if (!transporteService.insert(transporte))
				status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(transporte,status);

	}
	
	
	@GetMapping ("/consultar")
	@ApiOperation(value = "Consultar transportes", notes = "Consulta todos los transportes a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "find. Los transportes fueron encontrados correctamente.", response = Transporte.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Transporte>> allTransporte ()
	{
		ResponseEntity<List<Transporte>> response;
		List<Transporte> todos;
		
		todos = transporteService.findAll();

		
		return  new ResponseEntity<List<Transporte>>(todos,HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping ("/actualizar")
	@ApiOperation(value = "Actualizar transporte", notes = "Actualiza un transporte a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "modify. El transporte fue modificado correctamente.", response = Transporte.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modificado el transporte.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Transporte> modificarTransporte (@Valid @RequestBody Transporte transporte)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!transporteService.update(transporte))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(transporte,status);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar transporte", notes = "Elimina un transporte pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Remove. El transporte fue borrado correctamente.", response = Transporte.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el transporte.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Long> eliminarTransporte (@PathVariable Long id)
	{
		
		
		Optional<Transporte> op = transporteService.findById(id);
		Transporte transporte;
		
		
		if (op.isPresent()) {
			transporte = op.get();
			List<Solicitud> solicitudes = solicitudService.findAll();

			if (solicitudes.stream().anyMatch(s-> s.getTransporte().getId() == id)) {
				transporte.setActivo(false);
				transporteService.update(transporte);
				return new ResponseEntity<>(id, HttpStatus.OK);
			} else {
				if (!transporteService.delete(id))
					return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id, HttpStatus.OK);
		
	}
	
	@GetMapping("/nombre/{nombre}")
	@ApiOperation(value = "Buscar transporte", notes = "Busca un transporte pasandole el nombre a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Find. El transporte fue encontrado correctamente.", response = Transporte.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el transporte.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
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
