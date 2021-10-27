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
@Table(name = "MATERIAL")
public class Material implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8544406375475609869L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MATERIAL")
	@EqualsAndHashCode.Include
	private Long idMaterial;
	
	@Column(name = "PC")
	private int pc;
	
	@Column(name = "PORTATIL")
	private int portatil;
	
	@Column(name = "HDD")
	private int hdd;
	
	@Column(name = "SERVIDOR")
	private int servidor;
	
	@Column(name = "TELEFONOS")
	private int telefonos;
	
	@Column(name = "TABLET")
	private int tablet;
	
	@Column(name = "TFT")
	private int tft;
	
	@Column(name = "IMPRESORA")
	private int impresora;

	@Column(name = "CAJA_VARIOS")
	private int cajaVarios;
	
	@Column(name = "MATERIAL_EXTRA")
	private String extra;
	
	
}
