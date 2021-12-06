package com.api.iqtec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Estado;
import com.api.iqtec.service.interfaces.IEstadoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/Iqtec/estado")
@CrossOrigin(origins = "*")
public class EstadoController {
	
@Autowired IEstadoService estadoService;
	
	@GetMapping("/consultar")
	@ApiOperation(value = "Obtener estados", notes = "Obtiene un listado con los estados disponibles.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. La consulta de la solicitud fue correctamente.", response = Estado.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Estado>> obtenerEstados()
	{
		ResponseEntity<List<Estado>> response;
		List<Estado> todos;
		
		todos = estadoService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}

}
