package com.api.iqtec.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Estado;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.modelo.enums.NombreEstado;
import com.api.iqtec.service.interfaces.IEstadoService;
import com.api.iqtec.service.interfaces.ISeguimientoService;
import com.api.iqtec.service.interfaces.ISolicitudService;
import com.api.iqtec.service.interfaces.ITipoService;
import com.api.iqtec.service.interfaces.IUsuarioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/solicitud")
public class SolicitudController {
	
	@Autowired ISolicitudService solicitudService;
	@Autowired ITipoService tipoServices;
	@Autowired IUsuarioService userService;
	@Autowired IEstadoService estadoService;
	@Autowired ISeguimientoService seguimientoService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	@ApiOperation(value = "Crear solicitud", notes = "Agregar una nueva solicitud a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. La solicitud fue insertada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<Solicitud> insertarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.CREATED;
		
		Usuario usuario = userService.getByNombreUsuario(solicitud.getSeguimientos().get(0).getUsuario().getNombreUsuario()).get();
		
		solicitud.getSeguimientos().get(0).setUsuario(usuario);
		
		
		if (!solicitudService.insert(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	

	@GetMapping ("/consultar")
	@ApiOperation(value = "Consultar solicitudes", notes = "Consulta todas las solicitudes a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "find. Las solicitudes fueron encontradas correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<List<Solicitud>> obtenerTodasSolicitudes ()
	{
		ResponseEntity<List<Solicitud>> response;
		List<Solicitud> todos;
		
		todos = solicitudService.findAll();
		
		response = new ResponseEntity<>(todos,HttpStatus.OK);
		
		
		return response;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping ("/actualizar")
	@Transactional
	@ApiOperation(value = "Actualizar solicitud", notes = "Actualiza una solicitud a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "modify. La solicitud fue modificada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha modificado la solicitud.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<Solicitud> modificarSolicitud (@Valid @RequestBody Solicitud solicitud)
	{
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (!solicitudService.update(solicitud))
			status = HttpStatus.BAD_REQUEST;
		
		
		return new ResponseEntity<>(solicitud,status);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping ("/eliminar/{id}")
	@ApiOperation(value = "Eliminar solicitud", notes = "Elimina una solicitud pasandole el id a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. La solicitud fue borrada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra la solicitud.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<Long> eliminarSolicitud (@PathVariable Long id)
	{
	
	
		Solicitud solicitud = solicitudService.findById(id).get();
		
		if(solicitud.getSeguimientos().size() != 1) {
			return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		
		} else {
			
			if (!solicitudService.delete(id))
				return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id,HttpStatus.OK);
		
	}
	
	@GetMapping("/porReferencia/{referencia}")
	@ApiOperation(value = "Buscar solicitud", notes = "Busca una solicitud pasandole la referencia a la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Find. La solicitud fue encontrada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra la solicitud.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<Solicitud> findByReferencia(@PathVariable String referencia)
	{
		
		ResponseEntity<Solicitud> response;
		
		Optional<Solicitud> op = solicitudService.findByReferencia(referencia);
		
		if(op.isPresent())
			response = new ResponseEntity<Solicitud>(op.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<Solicitud>(HttpStatus.NOT_FOUND);
		
		
		return response;
	}
	

	@PutMapping (value = "/estado/{username}")
	@ApiOperation(value = "Cambio de Estado", notes = "Cambia al siguiente estado la solicitud indicada.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted. La solicitud fue modificada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha encontrado el id de la solicitud.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<?> cambioEstado (@PathVariable String username, @Valid @RequestBody Solicitud solicitud)
	{
		
		
		HttpStatus status = HttpStatus.ACCEPTED;
		
		
			
			Usuario usuario = userService.getByNombreUsuario(username).get();
			Estado estado;
			Seguimiento seguimiento = Seguimiento.builder()
					.fecha(LocalDateTime.now())
					.usuario(usuario)
					.build();
			
			int cuantos = solicitud.getSeguimientos().size();
			System.out.println("CUANTOS" + cuantos);
			switch (cuantos) {
			case 1:
				estado = estadoService.getByNombreEstado(NombreEstado.RECIBIDO).get();
				seguimiento.setEstado(estado);
				break;
			case 2: 
				estado = estadoService.getByNombreEstado(NombreEstado.PRODUCCION).get();
				seguimiento.setEstado(estado);
				break;
			case 3: 
				estado = estadoService.getByNombreEstado(NombreEstado.PROCESADO).get();
				seguimiento.setEstado(estado);
				break;
			case 4: 
				estado = estadoService.getByNombreEstado(NombreEstado.INFORME).get();
				seguimiento.setEstado(estado);
				break;
			case 5: 
				estado = estadoService.getByNombreEstado(NombreEstado.FINALIZADO).get();
				seguimiento.setEstado(estado);
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			solicitud.getSeguimientos().add(seguimiento);
			
			if (!solicitudService.update(solicitud))
				status = HttpStatus.BAD_REQUEST;
			
		

		return new ResponseEntity<Solicitud>(solicitud,status);
	}
	
	
	@PutMapping ("/seguimiento/{id}")
	@ApiOperation(value = "Nuevo Seguimiento", notes = "A??ade un nuevo seguimiento a una solicitud existente.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted. La solicitud fue modificada correctamente.", response = Solicitud.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se ha encontrado el id de la solicitud.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<Solicitud> modificarSolicitud (@PathVariable Long id, @Valid @RequestBody Seguimiento seguimiento)
	{
		Optional<Solicitud> opSolicitud = solicitudService.findById(id);
		Solicitud solicitud = null;

		HttpStatus status = HttpStatus.ACCEPTED;

		if (opSolicitud.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
		} else {
			solicitud = opSolicitud.get();
			seguimiento.setFecha(LocalDateTime.now());
			solicitud.getSeguimientos().add(seguimiento);

			if (!solicitudService.update(solicitud))
				status = HttpStatus.BAD_REQUEST;

		}

		return new ResponseEntity<>(solicitud,status);
	}

}
