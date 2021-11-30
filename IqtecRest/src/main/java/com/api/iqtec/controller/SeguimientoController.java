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

import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.service.interfaces.ISeguimientoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Iqtec/seguimiento")
public class SeguimientoController {
	

	@Autowired ISeguimientoService seguimientoService;

	@GetMapping("/consultar")
	public ResponseEntity<List<Seguimiento>> getSeguimientos ()
	{
		ResponseEntity<List<Seguimiento>> response;
		List<Seguimiento> todos;

		todos = seguimientoService.findAll();

		response = new ResponseEntity<>(todos,HttpStatus.OK);


		return response;
	}

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/actualizar")
	public ResponseEntity<Seguimiento> putSeguimientos(@RequestBody Seguimiento seguimiento){

		HttpStatus status = HttpStatus.ACCEPTED;

		if (!seguimientoService.update(seguimiento))
			status = HttpStatus.BAD_REQUEST;


		return new ResponseEntity<>(seguimiento,status);
	}

}
