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
import com.api.iqtec.modelo.Rol;
import com.api.iqtec.modelo.Seguimiento;
import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.modelo.enums.RolNombre;
import com.api.iqtec.security.dto.JwtDto;
import com.api.iqtec.security.dto.LoginUsuario;
import com.api.iqtec.security.dto.NuevoUsuario;
import com.api.iqtec.security.jwt.JwtProvider;
import com.api.iqtec.service.RolService;
import com.api.iqtec.service.UsuarioService;
import com.api.iqtec.service.interfaces.ISeguimientoService;
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
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthenticationManager authenticationManager;

    @Autowired UsuarioService usuarioService;

    @Autowired RolService rolService;

    @Autowired JwtProvider jwtProvider;

    @Autowired ISeguimientoService seguimientoService;
    
    
    
    @ApiOperation(value = "Crear Usuario", notes = "Registra un nuevo usuario en la aplicacion.")
    @ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El usuario fue insertado correctamente.", response = Usuario.class ),
			@ApiResponse(code = 400, message = "Bad Request. No se produce la insercion.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
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

    @ApiOperation(value = "Login", notes = "Comprueba la existencia del usuario y le genera un token de autentificaci??n." )
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK. El usuario exite y el token es generado con exito.", response = JwtDto.class ),
			@ApiResponse(code = 400, message = "Bad Request. El usuario no esta dado de alta en el sistema.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
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
    @ApiOperation(value = "Consultar Usuarios", notes = "Devuelve un listado con todos los usuarios de la aplicaci??n.")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK. Los usuarios se muestran correctamente.", response = Usuario.class ),
			@ApiResponse(code = 404, message = "Not Found. No se encuentran los usuarios.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
    public ResponseEntity<List<Usuario>>consultar(){
    	
    	ResponseEntity<List<Usuario>> response;
		List<Usuario> todos;
		
		
		todos = usuarioService.findAll();
		return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    @ApiOperation(value = "Obtener por Nombre", notes = "Obtiene a un usuario a traves de el nombre.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El usuario es recuperado de la base de datos", response = Usuario.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el usuario.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema.")})
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
			@ApiResponse(code = 200, message = "OK. El usuario fue borrado correctamente.", response = Usuario.class ),
			@ApiResponse(code = 404, message = "Not found. No se encuentra el usuario.", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error. Error inesperado del sistema."),
			@ApiResponse(code = 401, message = "Unauthorize. El usuario no posee los permisos para realizar la operaci??n." )})
	public ResponseEntity<?> eliminarUsuario (@PathVariable String username)
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
