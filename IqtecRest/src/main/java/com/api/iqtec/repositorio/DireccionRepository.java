package com.api.iqtec.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Direccion;

@Repository
public interface DireccionRepository extends CrudRepository<Direccion, Long> {

}
