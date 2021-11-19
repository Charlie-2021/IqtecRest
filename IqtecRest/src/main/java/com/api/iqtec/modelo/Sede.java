package com.api.iqtec.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "SEDES")
public class Sede implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8768639609132842613L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SEDE")
	@ApiModelProperty(hidden = true)
	private Long idSede;
	
	@Column(unique = true, name = "NOMBRE", length = 50)
	@NonNull
	@NotNull
	@ApiModelProperty(name = "Nombre",notes = "Nombra la sede", position = 0 )
	private String nombre;
	
	@Column(unique = true, name = "CIF", length = 25)
	@NonNull
	@NotNull
	@ApiModelProperty(name = "Cif",notes = "inserta el Cif de la sede",required = true, position = 1 )
	private String cif;
	
	
	@Embedded
	private Direccion direccion;
	
	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "SEDE_CONTACTO" , 
	      joinColumns = {  @JoinColumn ( name = "ID_SEDE" ,  referencedColumnName = "ID_SEDE" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_CONTACTO" , referencedColumnName = "ID_CONTACTO" ,  unique = true )  } 
	  ) 
	@Singular
	private List<Contacto> listaContactos;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;
	
	
}
