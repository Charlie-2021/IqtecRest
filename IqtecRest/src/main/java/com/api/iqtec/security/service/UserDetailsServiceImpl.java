package com.api.iqtec.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.iqtec.modelo.Usuario;
import com.api.iqtec.security.entity.UsuarioPrincipal;
import com.api.iqtec.service.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        return UsuarioPrincipal.build(usuario);
    }
}
