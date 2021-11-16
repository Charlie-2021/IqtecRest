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

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.service.interfaces.IClienteService;

import io.swagger.annotations.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/clientes")
public class ClienteController {

	@Autowired IClienteService clienteService;
	
	@PostMapping("/crear")
//	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@ApiOperation(value = "Crear cliente", notes = "Agregar un nuevo cliente a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El cliente fue insertado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Cliente> insertarCliente (@Valid @RequestBody Cliente cliente)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		if (!clienteService.insert(cliente))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(cliente,status);
	}
	
	
	@GetMapping ("/consultar")
	public ResponseEntity<List<Cliente>> obtenerTodosClientes ()
	{
		ResponseEntity<List<Cliente>> response;
		List<Cliente> todos;
		
		todos = clienteService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	@PutMapping ("/actualizar")
	public ResponseEntity<Cliente> modificarCliente (@Valid @RequestBody Cliente cliente)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!clienteService.update(cliente))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(cliente,status);
	}
	
	@DeleteMapping ("/eliminar/{id}")
	public ResponseEntity<Long> eliminarCliente (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		if (!clienteService.delete(id))
			status = HttpStatus.NOT_FOUND;
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	@GetMapping("/nombre/{nombre}")
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
