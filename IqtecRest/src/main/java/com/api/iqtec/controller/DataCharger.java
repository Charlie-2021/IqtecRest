package com.api.iqtec.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.Sede;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Tipo;
import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.modelo.enums.NombreEstado;
import com.api.iqtec.modelo.enums.RolNombre;
import com.api.iqtec.modelo.enums.TipoMaterial;
import com.api.iqtec.service.RolService;
import com.api.iqtec.service.UsuarioService;
import com.api.iqtec.service.interfaces.IClienteService;
import com.api.iqtec.service.interfaces.IEstadoService;
import com.api.iqtec.service.interfaces.IProyectoService;
import com.api.iqtec.service.interfaces.IRolService;
import com.api.iqtec.service.interfaces.ISedeService;
import com.api.iqtec.service.interfaces.ISolicitudService;
import com.api.iqtec.service.interfaces.ITipoService;
import com.api.iqtec.service.interfaces.ITransporteService;
import com.api.iqtec.service.interfaces.IUsuarioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/data")
@ApiIgnore
public class DataCharger {

	@Autowired IClienteService clienteService;
	
	@Autowired ISedeService sedeService;
	
	@Autowired ITransporteService transporteService;

	@Autowired IProyectoService proyectoService;
	
	@Autowired ISolicitudService solicitudService;
	
	@Autowired ITipoService tipoService;
	
	@Autowired RolService rolService;
	
	@Autowired UsuarioService usuarioService;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired IEstadoService estadoService;
	
	@PostMapping("/crear")
	public ResponseEntity<String> datos (){


		Rol rolAdmnid = Rol.builder().rolNombre(RolNombre.ROLE_ADMIN).build();
		Rol rolTecnico =  Rol.builder().rolNombre(RolNombre.ROLE_USER).build();
		
		Usuario userAdmin = new Usuario("admin", passwordEncoder.encode("admin"));
		Usuario userTecnico = new Usuario("tecnico", passwordEncoder.encode("tecnico"));
	
		userAdmin.setActivo(true);
		userTecnico.setActivo(true);
		
		
		Set<Rol> roles = new HashSet();
		roles.add(rolAdmnid);
		userAdmin.setRoles(roles);
		userAdmin.getRoles().add(rolTecnico);
		
		userTecnico.getRoles().add(rolTecnico);
		
		Estado solicitado = Estado.builder().nombreEstado(NombreEstado.SOLICITADO).build();
		Estado recibido = Estado.builder().nombreEstado(NombreEstado.RECIBIDO).build();
		Estado produccion = Estado.builder().nombreEstado(NombreEstado.PRODUCCION).build();
		Estado procesado = Estado.builder().nombreEstado(NombreEstado.PROCESADO).build();
		Estado informe = Estado.builder().nombreEstado(NombreEstado.INFORME).build();
		Estado finalizado = Estado.builder().nombreEstado(NombreEstado.FINALIZADO).build();
		
		Tipo tipo1 = Tipo.builder().tipoMaterial(TipoMaterial.HDD).build();
		Tipo tipo2 = Tipo.builder().tipoMaterial(TipoMaterial.CAJA_VARIOS).build();
		Tipo tipo3 = Tipo.builder().tipoMaterial(TipoMaterial.IMPRESORA).build();
		Tipo tipo4 = Tipo.builder().tipoMaterial(TipoMaterial.PC).build();
		Tipo tipo5 = Tipo.builder().tipoMaterial(TipoMaterial.PORTATIL).build();
		Tipo tipo6 = Tipo.builder().tipoMaterial(TipoMaterial.SERVIDOR).build();
		Tipo tipo7 = Tipo.builder().tipoMaterial(TipoMaterial.TABLET).build();
		Tipo tipo8 = Tipo.builder().tipoMaterial(TipoMaterial.TELEFONO).build();
		Tipo tipo9 = Tipo.builder().tipoMaterial(TipoMaterial.TFT).build();

		
		
		
		Transporte trans1 = Transporte.builder()
				.nombre("GLS")
				.cif("12345678A")
				.direccion(Direccion.builder()
						.calle("Av. de Castilla-la Mancha, 123")
						.cp("28701")
						.poblacion("San Sebastián de los Reyes")
						.provincia("Madrid")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Federico")
						.email("fede@gmail.com")
						.telefono1("654789213")
						.telefono2("911515151")
						.build())
				.build();

		Transporte trans2 = Transporte.builder()
				.nombre("HTM")
				.cif("12345678B")
				.direccion(Direccion.builder()
						.calle("C. los Frailes, 52")
						.cp("28814")
						.poblacion("Daganzo de Arriba")
						.provincia("Madrid")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Juan Ramirez")
						.email("juanrami@gmail.com")
						.telefono1("654789213")
						.telefono2("911515151")
						.build())
				.build();

		
		
		
		Cliente cli1 = Cliente.builder()
				.razonSocial("KPMG SA")
				.cif("12345678F")
				.direccion(Direccion.builder()
						.calle("Torre de Cristal, P. de la Castellana, 259C")
						.cp("28046")
						.poblacion("Madrid")
						.provincia("Madrid")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Jose Luis")
						.email("joselu@gmail.com")
						.telefono1("666333666")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Emilio Ruiz")
						.email("email1@gmail.com")
						.telefono1("666333666")
						.build())
				.build();

		Cliente cli2 = Cliente.builder()
				.razonSocial("EY (Ernst & Young)")
				.cif("89456132W")
				.direccion(Direccion.builder()
						.calle("C. de Raimundo Fernandez Villaverde, 65")
						.cp("28003")
						.poblacion("Madrid")
						.provincia("Madrid")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Luisa Gomez")
						.email("luisago@gmail.com")
						.telefono1("655655333")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Roberto Fernandez")
						.email("rofer@gmail.com")
						.telefono1("655655333")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Javier Delgado")
						.email("javidelgado@gmail.com")
						.telefono1("655655333")
						.build())
				.build();
		
		
		Sede sede1 = Sede.builder()
				.nombre("EY VALLADOLID")
				.cif("2A1541512")
				.direccion(Direccion.builder()
						.calle("C. de Santiago, 9")
						.cp("47001")
						.poblacion("Valladolid")
						.provincia("Valladolid")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Maria Ramos")
						.email("marira@gmail.com")
						.telefono1("655655444")
						.telefono2("911911231")
						.build())
				.cliente(cli2)
				.build();

		Sede sede2 = Sede.builder()
				.nombre("KPMG BARCELONA")
				.cif("82457892G")
				.direccion(Direccion.builder()
						.calle("Torre Realia BCN")
						.cp("08908")
						.poblacion("LHospitalet de Llobregat")
						.provincia("Barcelona")
						.pais("Espana")
						.build())
				.listaContacto(Contacto.builder()
						.nombre("Fernando")
						.email("fernando@gmail.com")
						.telefono1("655655777")
						.telefono2("911141232")
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
						.pais("Espana")
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
				.nombre("CISCO")
				.cliente(cli1)
				.descripcion("Hacer recuento por cajas o palets, puede venir de las dos maneras. Pesar en recepcion y reciclar por separado")
				.build();
				
		Solicitud solicitud = Solicitud.builder()
				.referencia("KPMAD-1121")
				.fechaRecogida(LocalDate.now().toString())
				.horario("09:00")
				.comentTransporte("Se requiere vehiculo con elevador. Hay control de acceso.")
				.proyecto(proyecto)
				.sede(sede3)
				.transporte(trans1)
				.seguimiento(Seguimiento.builder()
						.estado(solicitado)
						.fecha(LocalDateTime.now())
						.usuario(userAdmin)
						.build())
				.instrucciones(Instrucciones.builder()
						.comentInventario("Hacer recuento en recepcion.")
						.destruir(false)
						.degauss(false)
						.pesarReciclaje(false)
						.separarReciclaje(true)
						.reciclarTodo(false)
						.observaciones("El reciclaje debera identificarse con el nombre del cliente.")
						.build())
				.materiale(Material.builder()
						.tipo(tipo1)
						.cantidad(10)
						.build())
				.materiale(Material.builder()
						.tipo(tipo2)
						.cantidad(100)
						.build())
				.materiale(Material.builder()
						.tipo(tipo3)
						.cantidad(45)
						.build())
				.materiale(Material.builder()
						.tipo(tipo4)
						.cantidad(98)
						.build())
				.build();
		
		Solicitud solicitud2 = Solicitud.builder()
				.referencia("EYVLL-1021")
				.fechaRecogida(LocalDate.now().toString())
				.horario("19:00")
				.comentTransporte("Se precisa trepador de escaleras. Disponen de parking.")
				.sede(sede1)
				.transporte(trans1)
				.seguimiento(Seguimiento.builder()
						.estado(solicitado)
						.fecha(LocalDateTime.now())
						.usuario(userAdmin)
						.build())
				.seguimiento(Seguimiento.builder()
						.estado(recibido)
						.fecha(LocalDateTime.now())
						.usuario(userAdmin)
						.build())
				.instrucciones(Instrucciones.builder()
						.destruir(true)
						.degauss(false)
						.pesarReciclaje(true)
						.separarReciclaje(false)
						.reciclarTodo(true)
						.build())
				.materiales(Arrays.asList(
									Material.builder().tipo(tipo2).cantidad(107).build(),
									Material.builder().tipo(tipo1).cantidad(30).build(),
									Material.builder().tipo(tipo7).cantidad(25).build(),
									Material.builder().tipo(tipo3).cantidad(18).build()))
				.build();
		
		
		tipoService.save(tipo1);
		tipoService.save(tipo2);
		tipoService.save(tipo3);
		tipoService.save(tipo4);
		tipoService.save(tipo5);
		tipoService.save(tipo6);
		tipoService.save(tipo7);
		tipoService.save(tipo8);
		tipoService.save(tipo9);
		
		rolService.save(rolAdmnid);
		rolService.save(rolTecnico);
		
		usuarioService.save(userTecnico);
		usuarioService.save(userAdmin);
		
		estadoService.save(solicitado);
		estadoService.save(recibido);
		estadoService.save(produccion);
		estadoService.save(procesado);
		estadoService.save(informe);
		estadoService.save(finalizado);

		if (transporteService.insert(trans1) && transporteService.insert(trans2) && clienteService.insert(cli1) && clienteService.insert(cli2) &&
				sedeService.insert(sede1) && sedeService.insert(sede2) && sedeService.insert(sede3) && 
				proyectoService.insert(proyecto) && solicitudService.insert(solicitud) && solicitudService.insert(solicitud2))
			return new ResponseEntity<String> ("datos cargados corecctamente", HttpStatus.CREATED);

		return new ResponseEntity<String> ("Error al cargar datos", HttpStatus.NOT_FOUND);
	}
}
