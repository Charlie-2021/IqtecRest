package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "INSTRUCCIONES")
public class Instrucciones implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7574304156565438378L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_INSTRUCCIONES")
	@ApiModelProperty(hidden = true)
	private Long idInstrucciones;
	
	@Column(name = "COMENTARIO_INVENTARIO")
	@ApiModelProperty(name = "Comentario",notes = "Comentario del inventario", position = 0 )
	private String comentInventario;
	
	@Column(name = "DESTRUIR")
	@ApiModelProperty(name = "Destruir",notes = "Destruir material", position = 1 )
	private boolean destruir;
	
	@Column(name = "DEGAUSS")
	@ApiModelProperty(name = "Degauss",notes ="Eliminar el magnetismo de dispositivos", position = 2 )
	private boolean degauss;
	
	@Column(name = "PESAR_RECICLAJE")
	@ApiModelProperty(name = "Pesar reciclaje", position = 3 )
	private boolean pesarReciclaje;
	
	@Column(name = "SEPARAR_RECICLAJE")
	@ApiModelProperty(name = "Separar reciclaje", position = 4 )
	private boolean separarReciclaje;
	
	@Column(name = "RECICLAR_TODO")
	@ApiModelProperty(name = "Reciclar todo", position = 5 )
	private boolean reciclarTodo;
	
	@Column(name = "OBSEVACIONES")
	@ApiModelProperty(name = "Observaciones",notes = "Registra las observaciones", position = 6 )
	private String observaciones;
}
