package com.api.iqtec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.service.interfaces.IContactoService;
import com.api.iqtec.service.interfaces.ISolicitudService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/contactos")
public class ContactoController {

	@Autowired IContactoService contactoService;
	
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar contacto", notes = "Elimina un cliente pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El cliente fue borrado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el cliente.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Long> eliminarCliente (@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.OK;
		
		
		
		if (!contactoService.delete(id)) {
			status = HttpStatus.NOT_FOUND;
		} else {
			
		}
			
		
		
		return new ResponseEntity<>(id,status);
		
	}
	
	
	
}
