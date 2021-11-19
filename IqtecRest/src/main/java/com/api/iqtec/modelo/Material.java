package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "MATERIALES")
public class Material implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8544406375475609869L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MATERIAL")
	@EqualsAndHashCode.Include
	@ApiModelProperty(hidden = true)
	private Long idMaterial;
	
	@OneToOne
	@JoinColumn(name = "ID_TIPO")
	@NotNull
	private Tipo tipo;
	
	@NotNull
	@Column(name = "CANTIDAD")
	@ApiModelProperty(name = "Cantidad",notes = "Idica la antidad del material", required = true, position = 0 )
	private int cantidad;
	
	
}
