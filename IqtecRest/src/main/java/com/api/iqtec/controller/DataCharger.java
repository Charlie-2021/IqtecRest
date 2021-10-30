package com.api.iqtec.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.Instrucciones;
import com.api.iqtec.modelo.Material;
import com.api.iqtec.modelo.Proyecto;
import com.api.iqtec.modelo.Sede;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.service.IClienteService;
import com.api.iqtec.service.IProyectoService;
import com.api.iqtec.service.ISedeService;
import com.api.iqtec.service.ISolicitudService;
import com.api.iqtec.service.ITransporteService;

@RestController
@RequestMapping("/data")
public class DataCharger {

	@Autowired IClienteService clienteService;
	
	@Autowired ISedeService sedeService;
	
	@Autowired ITransporteService transporteService;

	@Autowired IProyectoService proyectoService;
	
	@Autowired ISolicitudService solicitudService;
	
	@PostMapping("/crear")
	public ResponseEntity<String> datos (){


		Transporte trans1 = Transporte.builder()
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
				.build();

		Cliente cli2 = Cliente.builder()
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
				.listaContacto(Contacto.builder()
						.nombre("jose")
						.email("email2@gmail.com")
						.telefono1("655655333")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("javier")
						.email("email2@gmail.com")
						.telefono1("655655333")
						.build())
				.build();
		
		
		Sede sede1 = Sede.builder()
				.nombre("SEDE 1")
				.cif("2A15415")
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
				.cliente(cli1)
				.build();

		Sede sede2 = Sede.builder()
				.nombre("SEDE 2")
				.cif("2A158748")
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
				.cliente(cli1)
				.build();
		
		Sede sede3 = Sede.builder()
				.nombre("SEDE 3")
				.cif("255151415")
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
				.cliente(cli2)
				.build();
		
		Proyecto proyecto = Proyecto.builder()
				.nombre("PROYECTO 1")
				.cliente(cli1)
				.descripcion("BREVE DESCRIPCION")
				.build();
				
		Solicitud solicitud = Solicitud.builder()
				.referencia("Solicitud 1")
				.fechaRecogida(LocalDate.now().toString())
				.horario("09:00")
				.comentTransporte("comentario para el transporte")
				.estado(Estado.SOLICITADO)
				.proyecto(proyecto)
				.sede(sede3)
				.transporte(trans1)
				.seguimiento(Seguimiento.builder()
						.fechaCreacion(LocalDateTime.now())
						.build())
				.instrucciones(Instrucciones.builder()
						.comentInventario("COMENTARIO INVENTARIO")
						.destruir(false)
						.degauss(false)
						.pesarReciclaje(false)
						.separarReciclaje(true)
						.reciclarTodo(false)
						.observaciones("por si hubiera algo que añadir")
						.build())
				.material(Material.builder()
						.pc(100)
						.portatil(200)
						.hdd(0)
						.servidor(5)
						.telefonos(150)
						.tablet(0)
						.tft(23)
						.impresora(0)
						.cajaVarios(2)
						.build())
				.build();
		

		if (transporteService.insert(trans1) && clienteService.insert(cli1) && clienteService.insert(cli2) &&
				sedeService.insert(sede1) && sedeService.insert(sede2) && sedeService.insert(sede3) && 
				proyectoService.insert(proyecto) && solicitudService.insert(solicitud))
			return new ResponseEntity<String> ("datos cargados corecctamente", HttpStatus.CREATED);
//		
//		if (clienteService.insert(cli1) && clienteService.insert(cli2))
//			return new ResponseEntity<String> ("datos cargados corecctamente bien", HttpStatus.CREATED);
//		
//		if (sedeService.insert(sede1) && sedeService.insert(sede2) && sedeService.insert(sede3))
//			return new ResponseEntity<String> ("datos cargados corecctamente", HttpStatus.CREATED);
		
		

		return new ResponseEntity<String> ("Error al cargar datos", HttpStatus.NOT_FOUND);
	}
}
