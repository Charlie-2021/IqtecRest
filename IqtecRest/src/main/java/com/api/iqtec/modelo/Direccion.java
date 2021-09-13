package com.api.iqtec.modelo;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Embeddable
public class Direccion {

	private String calle;
	
	private String cp;
	
	private String poblacion;
	
	private String provincia;
	
	private String pais;
	
	
}
