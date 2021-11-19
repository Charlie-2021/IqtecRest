package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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

@Embeddable
public class Direccion {

	
	@Column(name = "CALLE", length =200)
	@ApiModelProperty(name = "Calle", position = 0 )
	private String calle;
	
	@Column(name = "CODIGO_POSTAL", length =25)
	@ApiModelProperty(name = "Codigo postal", position = 1 )
	private String cp;
	
	@Column(name = "LOCALIDAD", length =200)
	@ApiModelProperty(name = "Población", position = 2 )
	private String poblacion;
	
	@Column(name = "PROVINCIA", length =200)
	@ApiModelProperty(name = "Provincia", position = 3 )
	private String provincia;
	
	@Column(name = "PAIS", length =100)
	@ApiModelProperty(name = "Pais", position = 4 )
	private String pais;
	
	
}
