package com.api.iqtec.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.iqtec.modelo.Material;

@Repository
public interface MaterialRepositorio extends CrudRepository<Material, Long> {

}
