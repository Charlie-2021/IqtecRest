package com.api.iqtec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.service.interfaces.ITipoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tipo")
public class TipoController {

	@Autowired ITipoService tipoService;
	
	@GetMapping("/consultar")
	@ApiOperation(value = "Consultar Tipos de Materiales", notes = "Consulta todos los tipos de materiales disponibles a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "find. Los tipos fueron encontradas correctamente.", response = Tipo.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<List<Tipo>> obtenerTodosTipos(){

		List<Tipo> tipos = tipoService.findAll();
	
		return new ResponseEntity<List<Tipo>> (tipos, HttpStatus.OK);
	}
}
