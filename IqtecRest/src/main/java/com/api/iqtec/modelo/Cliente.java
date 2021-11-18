package com.api.iqtec.modelo;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
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
@Table(name = "CLIENTES")
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1776598221198292715L;

	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CLIENTE")
	@Hidden
	@ApiModelProperty(hidden = true)
	private Long idCliente;
	
	@Column(unique = true, name = "RAZON_SOCIAL", length = 50)
	@NonNull
	@NotNull
	@ApiModelProperty(name = "Razon Social", notes = "Nombre del fiscal cliente.", required = true, position = 0 )
	private String razonSocial;
	
	@Column(name = "CIF", length = 10)
	@ApiModelProperty(notes = "Codigo de Identificacion Fiscal del cliente.",required = true, position = 1)
	private String cif;
	
	@Embedded
	private Direccion direccion;
	
	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "CLIENTE_CONTACTO" , 
	      joinColumns = {  @JoinColumn ( name = "ID_CLIENTE" ,  referencedColumnName = "ID_CLIENTE" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_CONTACTO" , referencedColumnName = "ID_CONTACTO" ,  unique = true ) }
	      
	  ) 
	@Singular
	private List<Contacto> listaContactos;
	
}
