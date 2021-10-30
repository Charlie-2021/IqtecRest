package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "PROYECTOS")
public class Proyecto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6575311054570890232L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_PROYECTO")
	private Long idProyecto;
	
	@Column(name = "NOMBRE", unique = true, length = 25)
	@NotNull
	private String nombre;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE")
	@NotNull
	private Cliente cliente;
}