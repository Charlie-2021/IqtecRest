package com.api.iqtec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.service.interfaces.ISeguimientoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/seguimiento")
public class SeguimientoController {
	

	@Autowired ISeguimientoService seguimientoService;

	@GetMapping("/consultar")
	@ApiOperation(value = "Consultar seguimientos", notes = "Consulta todos los seguimientos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. La consulta de los seguimientos fue correctamenta.", response = Seguimiento.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Seguimiento>> getSeguimientos ()
	{
		ResponseEntity<List<Seguimiento>> response;
		List<Seguimiento> todos;

		todos = seguimientoService.findAll();

		response = new ResponseEntity<>(todos,HttpStatus.OK);


		return response;
	}

	

	@PutMapping("/actualizar")
	@ApiOperation(value = "Añadir Seguimiento", notes = "Añade un nuevo seguimiento a una solicitud.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted. El seguimiento fue modificado correctamente.", response = Seguimiento.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modificado el seguimiento.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<Seguimiento> putSeguimientos(@RequestBody Seguimiento seguimiento){

		HttpStatus status = HttpStatus.ACCEPTED;
	

		if (!seguimientoService.update(seguimiento))
			status = HttpStatus.BAD_REQUEST;


		return new ResponseEntity<>(seguimiento,status);
	}

}
