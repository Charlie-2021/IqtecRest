package com.api.iqtec.controller;

import javax.validation.Valid;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Contacto;
import com.api.iqtec.modelo.Direccion;
import com.api.iqtec.modelo.Sede;
import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.service.IClienteService;
import com.api.iqtec.service.ISedeService;
import com.api.iqtec.service.ITransporteService;

@RestController
@RequestMapping("/data")
public class DataCharger {

	
	//COMENTARIO PARA FELIX
	private String miString = "";
	
	@Autowired IClienteService clienteService;
	
	@Autowired ISedeService sedeService;
	
	@Autowired ITransporteService transporteService;

	@PostMapping("/crear")
	public ResponseEntity<String> datos (){


		Transporte trans1 = Transporte.builder()
				.id(1l)
				.nombre("GLS")
				.cif("1215151D")
				.direccion(Direccion.builder()
						.calle("calle transporte 1")
						.cp("28806")
						.poblacion("getafe")
						.provincia("madrid")
						.pais("España")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("federico")
						.email("email6@gmail.com")
						.telefono1("654789213")
						.telefono2("911515151")
						.build())
				.build();



		Cliente cli1 = Cliente.builder()
				.idCliente(1l)
				.razonSocial("KPMG SA")
				.cif("123456A")
				.direccion(Direccion.builder()
						.calle("calle cliente 1")
						.cp("28803")
						.poblacion("alcala de henares")
						.provincia("Madrid")
						.pais("España")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("jose")
						.email("email1@gmail.com")
						.telefono1("666333666")
						.build())
				.sede(Sede.builder()
						.nombre("SEDE 1")
						.direccion(Direccion.builder()
								.calle("calle sede 1")
								.cp("80653")
								.poblacion("Barcelona")
								.provincia("Barcelona")
								.pais("España")
								.build())
						.listaContacto(Contacto.builder()
								.nombre("maria")
								.email("email3@gmail.com")
								.telefono1("655655444")
								.telefono2("91191123")
								.build())
						.build())
				.sede(Sede.builder()
						.nombre("SEDE 2")
						.direccion(Direccion.builder()
								.calle("calle sede 2")
								.cp("45120")
								.poblacion("valencia")
								.provincia("valencia")
								.pais("España")
								.build())
						.listaContacto(Contacto.builder()
								.nombre("fernando")
								.email("email4@gmail.com")
								.telefono1("655655777")
								.telefono2("91114123")
								.build())
						.build())
				.build();

		Cliente cli2 = Cliente.builder()
				.idCliente(2l)
				.razonSocial("EYEE SA")
				.cif("123456B")
				.direccion(Direccion.builder()
						.calle("calle cliente 2")
						.cp("28030")
						.poblacion("madrid")
						.provincia("Madrid")
						.pais("España")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("felipe")
						.email("email2@gmail.com")
						.telefono1("655655333")
						.build())
				.sede(Sede.builder()
						.nombre("SEDE 3")
						.direccion(Direccion.builder()
								.calle("calle sede 3")
								.cp("25981")
								.poblacion("Toledo")
								.provincia("Toledo")
								.pais("España")
								.build())
						.listaContacto(Contacto.builder()
								.nombre("jesus")
								.email("email7@gmail.com")
								.telefono1("65151515")
								.telefono2("94502515")
								.build())
						.build())
				.build();
		
		

		if (transporteService.insert(trans1))
			return new ResponseEntity<String> ("datos cargados corecctamente", HttpStatus.CREATED);
		
		if (clienteService.insert(cli1) && clienteService.insert(cli2))
			return new ResponseEntity<String> ("datos cargados corecctamente bien", HttpStatus.CREATED);

		return new ResponseEntity<String> ("Error al cargar datos", HttpStatus.NOT_FOUND);
	}
}
