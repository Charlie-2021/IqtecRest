package com.api.iqtec.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.service.interfaces.IProyectoService;
import com.api.iqtec.service.interfaces.ISolicitudService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/proyectos")
public class ProyectoController {

	
	@Autowired IProyectoService proyectoService;
	@Autowired ISolicitudService solicitudService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	@ApiOperation(value = "Crear proyecto", notes = "Agregar un nuevo proyecto a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El proyecto fue insertado correctamente.", response = Proyecto.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Proyecto> insertarProyecto (@Valid @RequestBody Proyecto proyecto)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		Optional<Proyecto> op = proyectoService.findById(proyecto.getIdProyecto());
		
		if (op.isPresent()) {
			Proyecto noActivo = op.get();
			proyecto.setActivo(true);
			proyecto.setIdProyecto(noActivo.getIdProyecto());
			proyectoService.update(proyecto);
			
		} else {
			proyecto.setActivo(true);
			if (!proyectoService.insert(proyecto))
				status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(proyecto,status);
	}
	
	

	@GetMapping ("/consultar")
	@ApiOperation(value = "Consultar proyectos", notes = "Consulta todos los proyectos a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "find. Los proyectos fueron encontrados correctamente.", response = Proyecto.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Proyecto>> obtenerTodosProyectos ()
	{
		ResponseEntity<List<Proyecto>> response;
		List<Proyecto> todos;
		
		todos = proyectoService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping ("/actualizar")
	@ApiOperation(value = "Actualizar proyecto", notes = "Actualiza un proyecto a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted. El proyecto fue modificado correctamente.", response = Proyecto.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modificado el proyecto.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Proyecto> modificarProyecto (@Valid @RequestBody Proyecto proyecto)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!proyectoService.update(proyecto))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(proyecto,status);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar proyecto", notes = "Elimina un proyecto pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Remove. El proyecto fue borrado correctamente.", response = Proyecto.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el proyecto.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Long> eliminarProyecto (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		Optional<Proyecto> op = proyectoService.findById(id);
		Proyecto proyecto;
		
		
		if (op.isPresent()) {
			proyecto = op.get();
			List<Solicitud> solicitudes = solicitudService.findAll();
			
			List<Solicitud> conProyecto =solicitudes.stream().filter(s-> s.getProyecto() != null).collect(Collectors.toList());
			
			if (conProyecto.stream().anyMatch(s-> s.getProyecto().equals(proyecto))) {
				proyecto.setActivo(false);
				proyectoService.update(proyecto);
				return new ResponseEntity<>(id, HttpStatus.OK);
			} else {
				if (!proyectoService.delete(id))
					return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
			}
			
		} else {
			
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id, HttpStatus.OK);
		
	}
}
