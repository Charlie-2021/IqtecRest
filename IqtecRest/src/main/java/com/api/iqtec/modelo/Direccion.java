package com.api.iqtec.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "DIRECCION")
public class Direccion {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_DIRECCION")
	private Long idDrireccion;
	
	@NonNull
	@NotNull
	@Column(name = "CALLE", length =200, nullable = false)
	private String calle;
	
	@NonNull
	@NotNull
	@Column(name = "CODIGO_POSTAL", length =25, nullable = false)
	private String cp;
	
	@NonNull
	@NotNull
	@Column(name = "LOCALIDAD", length =200, nullable = false)
	private String poblacion;
	
	@NonNull
	@NotNull
	@Column(name = "PROVINCIA", length =200, nullable = false)
	private String provincia;
	
	@NonNull
	@NotNull
	@Column(name = "PAIS", length =100, nullable = false)
	private String pais;
	
	
}
