package com.api.iqtec.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "SEGUIMIENTO")
public class Seguimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350277790011664395L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_SEGUIMIENTO")
	private Long idSeguimiento;
	
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaRecibido;
	private LocalDateTime fechaProduccion;
	private LocalDateTime fechaProcesado;
	private LocalDateTime fechaInforme;
	private LocalDateTime fechaFinalizado;

}
