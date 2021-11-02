package com.api.iqtec.security.controller;


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

import com.api.iqtec.security.dto.JwtDto;
import com.api.iqtec.security.dto.NuevoUsuario;
import com.api.iqtec.security.entity.Rol;
import com.api.iqtec.security.entity.Usuario;
import com.api.iqtec.security.enums.RolNombre;
import com.api.iqtec.security.jwt.JwtProvider;
import com.api.iqtec.security.service.imp.RolService;
import com.api.iqtec.security.service.imp.UsuarioService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthenticationManager authenticationManager;

    @Autowired UsuarioService usuarioService;

    @Autowired RolService rolService;

    @Autowired JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<String> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
    	
        if(bindingResult.hasErrors())
            return new ResponseEntity<String>(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity<String>( "EL NOMBRE DE USUARIO YA EXISTE", HttpStatus.BAD_REQUEST);
        
  
        
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        Set<Rol> roles = new HashSet<>();
        
        roles.add(rolService.getByRolNombre(RolNombre.TECNICO).get());
        
        if(nuevoUsuario.getRoles().contains("ADMINISTRADOR"))
            roles.add(rolService.getByRolNombre(RolNombre.ADMINISTRADOR).get());
        
        usuario.setRoles(roles);
        
        usuarioService.insert(usuario);
        
        return new ResponseEntity<String>("USUARIO CREADO CORRECTAMENTE", HttpStatus.CREATED);
    }

    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
        
    	if(bindingResult.hasErrors())
            return new ResponseEntity<JwtDto>(HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getNombreUsuario(), usuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateToken(authentication);
        
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }
}
