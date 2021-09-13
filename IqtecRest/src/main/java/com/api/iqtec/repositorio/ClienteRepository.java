package com.api.iqtec.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

	public Optional<Cliente> findByNombre (String nombre);
}
