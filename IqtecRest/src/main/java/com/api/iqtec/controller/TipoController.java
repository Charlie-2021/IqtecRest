package com.api.iqtec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.service.interfaces.ITipoService;

@RestController
@CrossOrigin("/*")
@RequestMapping("/tipo")
public class TipoController {

	@Autowired ITipoService tipoService;
	
	@GetMapping("/consultar")
	public ResponseEntity<List<Tipo>> obtenerTodosTipos(){

		List<Tipo> tipos = tipoService.findAll();
	
		return new ResponseEntity<List<Tipo>> (tipos, HttpStatus.OK);
	}
}
