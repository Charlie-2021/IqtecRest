package com.api.iqtec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.api.iqtec.modelo.Cliente;
import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Solicitud;
import com.api.iqtec.modelo.Transporte;
import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.modelo.enums.RolNombre;
import com.api.iqtec.security.dto.JwtDto;
import com.api.iqtec.security.dto.LoginUsuario;
import com.api.iqtec.security.dto.NuevoUsuario;
import com.api.iqtec.security.jwt.JwtProvider;
import com.api.iqtec.service.RolService;
import com.api.iqtec.service.UsuarioService;
import com.api.iqtec.service.interfaces.ISeguimientoService;
import com.api.iqtec.service.interfaces.ISolicitudService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthenticationManager authenticationManager;

    @Autowired UsuarioService usuarioService;

    @Autowired RolService rolService;

    @Autowired JwtProvider jwtProvider;

    @Autowired ISeguimientoService seguimientoService;
    
    
    @ApiOperation("Crea un nuevo usuario en la aplicacion.")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
    	
        if(bindingResult.hasErrors())
            return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
        
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity<String>( "EL NOMBRE DE USUARIO YA EXISTE", HttpStatus.BAD_REQUEST);
        
  
        
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        
        usuario.setRoles(roles);
        usuario.setActivo(true);
        usuarioService.save(usuario);
        
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @ApiOperation("Si el usuario es valido devuelve un Json Web Token.")
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        
    	
    	
    	
    	if(bindingResult.hasErrors())
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
    	
    	if (usuarioService.existsByNombreUsuario(loginUsuario.getNombreUsuario())) {
    		Usuario u = usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario()).get();
    		if(!u.isActivo())
    			 return new ResponseEntity("El usuario no exite", HttpStatus.BAD_REQUEST);
    			
    	}
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateToken(authentication);
        
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
    
    
    @GetMapping("/consultar")
    public ResponseEntity<List<Usuario>>consultar(){
    	
    	ResponseEntity<List<Usuario>> response;
		List<Usuario> todos;
		
		
		todos = usuarioService.findAll();
		System.out.println(todos.toString());
		return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?>getByNombreUsuario(@PathVariable String nombre){
    	ResponseEntity<?> response;
		
    	Usuario usuario;
		Optional<Usuario> op = usuarioService.getByNombreUsuario(nombre);
		
		if (op.isPresent()) {
			usuario = op.get();
			response = new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("No existe ningun usuario.", HttpStatus.NOT_FOUND);	
		}
		
		return response;
    }
    
    @DeleteMapping ("/eliminar/{username}")
	@ApiOperation(value = "Eliminar Usuario", notes = "Elimina un usuario de la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El cliente fue borrado correctamente.", response = Cliente.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el cliente.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operación." )})
	public ResponseEntity<String> eliminarUsuario (@PathVariable String username)
	{
    	
    	Optional<Usuario> op = usuarioService.getByNombreUsuario(username);
		Usuario usuario;
		
		
		if (op.isPresent()) {
			usuario = op.get();
			List<Seguimiento> seg = seguimientoService.findAll();

			
			if (seg.stream().anyMatch(s-> s.getUsuario().getId()== usuario.getId())) {
				usuario.setActivo(false);
				usuarioService.update(usuario);
				return new ResponseEntity<>(username, HttpStatus.OK);
			} else {
				if (!usuarioService.delete(usuario.getId()))
					return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(username, HttpStatus.OK);
		
	}
}
