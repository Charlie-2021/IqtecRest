package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Long idInstrucciones;
	
	@Column(name = "COMENTARIO_INVENTARIO")
	private String comentInventario;
	
	@Column(name = "DESTRUIR")
	private boolean destruir;
	
	@Column(name = "DEGAUSS")
	private boolean degauss;
	
	@Column(name = "PESAR_RECICLAJE")
	private boolean pesarReciclaje;
	
	@Column(name = "SEPARAR_RECICLAJE")
	private boolean separarReciclaje;
	
	@Column(name = "RECICLAR_TODO")
	private boolean reciclarTodo;
	
	@Column(name = "OBSEVACIONES")
	private String observaciones;
}
