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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "TRANSPORTES")
public class Transporte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8701575591751408392L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRANSPORTE")
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@Column(unique = true, name = "NOMBRE", length = 50, nullable = false)
	@NonNull
	@NotNull
	@ApiModelProperty(name = "Nombre",notes = "Nombra el transporte",required = true, position = 0 )
	private String nombre;
	
	@Column(name = "CIF", length = 20, nullable = false)
	@NonNull
	@NotNull
	@ApiModelProperty(name = "Cif",notes = "Inserta el Cif del transporte",required =true, position = 1 )
	private String cif;
	
	@Embedded
	private Direccion direccion;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable 
	  ( 
	      name = "TRANSPORTE_CONTACTO" , 
	      joinColumns = {  @JoinColumn ( name = "ID_TRANSPORTE" ,  referencedColumnName = "ID_TRANSPORTE" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_CONTACTO" , referencedColumnName = "ID_CONTACTO" ,  unique = true, insertable = true )  } 
	  ) 
	@Singular
	private List<Contacto> listaContactos;
	
	 @Column(name = "ACTIVO")
	 @ApiModelProperty(hidden = true)
	 private boolean activo;
}
