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
import com.api.iqtec.service.interfaces.ISedeService;
import com.api.iqtec.service.interfaces.ISolicitudService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Iqtec/sedes")
public class SedeController {

	@Autowired ISedeService sedeService;
	@Autowired ISolicitudService solicitudService;
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	@ApiOperation(value = "Crear sede", notes = "Agregar una nueva sede a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. La sede fue insertada correctamente.", response = Sede.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Sede> insertarSede (@Valid @RequestBody Sede sede)
	{
		HttpStatus status = HttpStatus.CREATED;
		
		Optional<Sede> op = sedeService.findByNombre(sede.getNombre());
		
		if (op.isPresent()) {
			Sede sedeNoActiva = op.get();
			sede.setActivo(true);
			sede.setIdSede(sedeNoActiva.getIdSede());
			sedeService.update(sede);
			
		} else {
			sede.setActivo(true);
			if (!sedeService.insert(sede))
				status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(sede,status);
	}
	
	
	@GetMapping ("/consultar")
	@ApiOperation(value = "Consultar sedes", notes = "Consulta todas las sedes a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Find. Las sedes fueron encontradas correctamente.", response = Sede.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Sede>> obtenerSedes ()
	{

		List<Sede> todos;
		todos = sedeService.findAll();
		
		return new ResponseEntity<List<Sede>>(todos,HttpStatus.OK);
	
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping ("/actualizar")
	@ApiOperation(value = "Actualizar sede", notes = "Actualiza una sede a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Modify. La sede fue modificada correctamente.", response = Sede.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modificado la sede.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Sede> modificarSede (@Valid @RequestBody Sede sede)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!sedeService.update(sede))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(sede,status);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar sede", notes = "Elimina una sede pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Remove. La sede fue borrada correctamente.", response = Sede.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra la sede.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Long> eliminarSede (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		Optional<Sede> op = sedeService.findById(id);
		Sede sede;
		
		
		if (op.isPresent()) {
			sede = op.get();
			List<Solicitud> solicitudes = solicitudService.findAll();
			
			if (solicitudes.stream().anyMatch(s-> s.getSede().getIdSede() == id)) {
				sede.setActivo(false);
				sedeService.update(sede);
				return new ResponseEntity<>(id, HttpStatus.OK);
			} else {
				if (!sedeService.delete(id))
					return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
			}
			
		} else {
			
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id, HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/nombre/{nombre}")
	@ApiOperation(value = "Buscar sede", notes = "Busca una sede pasandole el nombre a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Find. La sede fue encontrada correctamente.", response = Sede.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra la sede.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
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
