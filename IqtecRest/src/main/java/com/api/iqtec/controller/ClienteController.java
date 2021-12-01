package com.api.iqtec.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.modelo.Sede;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.service.interfaces.IClienteService;
import com.api.iqtec.service.interfaces.IProyectoService;
import com.api.iqtec.service.interfaces.ISedeService;
import com.api.iqtec.service.interfaces.ISolicitudService;

import io.swagger.annotations.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/clientes")
public class ClienteController {

	@Autowired IClienteService clienteService;
	
	@Autowired ISedeService sedeService;
	
	@Autowired ISolicitudService solicitudService;
	
	@Autowired IProyectoService proyectoService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	@ApiOperation(value = "Crear cliente", notes = "Agregar un nuevo cliente a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El cliente fue insertado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Cliente> insertarCliente (@Valid @RequestBody Cliente cliente)
	{
		HttpStatus status = HttpStatus.CREATED;
		
		Optional<Cliente> op = clienteService.findByNombre(cliente.getRazonSocial());
		
		if (op.isPresent()) {
			Cliente clienteNoActivo = op.get();
			cliente.setActivo(true);
			cliente.setIdCliente(clienteNoActivo.getIdCliente());
			clienteService.update(cliente);
			
		} else {
			cliente.setActivo(true);
			if (!clienteService.insert(cliente))
				status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(cliente,status);
	}
	
	@GetMapping ("/consultar")
	@ApiOperation(value = "Consultar clientes", notes = "Consulta todos los clientes a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Los clientes fueron encontrados correctamente.", response = Cliente.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Cliente>> obtenerTodosClientes ()
	{
		ResponseEntity<List<Cliente>> response;
		List<Cliente> todos;
		
		todos = clienteService.findAll();	
		response = new ResponseEntity<>(todos, HttpStatus.OK);
		
		
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping ("/actualizar")
	@ApiOperation(value = "Actualizar cliente", notes = "Actualiza un cliente a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted. El cliente fue modificado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modifica el cliente.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Cliente> modificarCliente (@Valid @RequestBody Cliente cliente)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!clienteService.update(cliente))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(cliente,status);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar cliente", notes = "Elimina un cliente pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El cliente fue borrado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el cliente.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Long> eliminarCliente (@PathVariable Long id)
	{

		Optional<Cliente> op = clienteService.findById(id);
		
		if(op.isPresent()) 
		{
			
			Cliente cliente = op.get();
			
			List<Sede> sedes = sedeService.findAll();
			List<Solicitud> solicitudes = solicitudService.findAll();
			List<Proyecto> proyectos = proyectoService.findAll();
			
		
			
			if (sedes.stream().anyMatch(s -> s.getCliente().getIdCliente() == id) || solicitudes.stream().anyMatch(s-> s.getSede().getCliente().getIdCliente()== id)
					|| proyectos.stream().anyMatch(p-> p.getCliente().getIdCliente() == id)) {
				cliente.setActivo(false);
				clienteService.update(cliente);
				return new ResponseEntity<>(id, HttpStatus.OK);
			} else {
				
				if (!clienteService.delete(id))
					return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
			}		 
			 
		} else {
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<>(id, HttpStatus.OK);
		
	}
	
	@GetMapping("/nombre/{nombre}")
	@ApiOperation(value = "Buscar cliente", notes = "Busca un cliente pasandole el nombre a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Find. El cliente fue encontrado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el cliente.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Cliente> findByNombre(@PathVariable String nombre)
	{
		
		ResponseEntity<Cliente> response;
		
		Optional<Cliente> op = clienteService.findByNombre(nombre);
		
		if(op.isPresent())
			response = new ResponseEntity<Cliente>(op.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		
		
		return response;
	}
}
