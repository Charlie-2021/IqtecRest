package com.api.iqtec.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.iqtec.modelo.enums.TipoMaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TIPOS")
public class Tipo {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_TIPO")
	private Long idTipo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_MATERIAL", unique = true)
	@EqualsAndHashCode.Include
	private TipoMaterial tipoMaterial;
}
