package com.api.iqtec.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Contacto;

@Repository
public interface ContactoRepository extends CrudRepository<Contacto, Long> {

}
