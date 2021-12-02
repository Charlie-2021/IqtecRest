package com.api.iqtec.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "SOLICITUDES")
public class Solicitud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3470447350460052439L;


	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOLICITUD")
	@ApiModelProperty(hidden = true)
	private Long idSolicitud;
	
	
	@NonNull
	@NotNull
	@Column(name = "REFERENCIA", length = 50, unique = true)
	@ApiModelProperty(name = "Referencia",required = true, notes = "Inserta la referencia de la solicitud", position = 0 )
	private String referencia;
	
	
	@Column(name = "FECHA_RECOGIDA")
	@ApiModelProperty(name = "Fecha",notes = "Inserta la fecha de recogida del seguimiento", position = 1 )
	private String fechaRecogida;
	
	@Column(name = "HORARIO", length = 50)
	@ApiModelProperty(name = "Horario",notes = "Inserta el horario del seguimiento", position = 2)
	private String horario;
	
	@Column(name = "COMENTARIO_TRANSPORTE")
	@ApiModelProperty(name = "Comentario",notes = "Añade un comentario al transporte", position = 3 )
	private String comentTransporte;
	
	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "MATERIAL_SOLICITUD" , 
	      joinColumns = {  @JoinColumn ( name = "ID_SOLICITUD" ,  referencedColumnName = "ID_SOLICITUD" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_MATERIAL" , referencedColumnName = "ID_MATERIAL" ,nullable = true,updatable = true,  unique = true ) }
	      
	  ) 
	@Singular
	private List<Material> materiales;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROYECTO", nullable = true, insertable = true)
	private Proyecto proyecto;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SEDE", nullable = false)
	@NotNull
	private Sede sede;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ID_TRANSPORTE")
	@NotNull
	private Transporte transporte;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_INSTRUCCIONES")
	private Instrucciones instrucciones;
	

	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "SEGUIMIENTO_SOLICITUD" , 
	      joinColumns = {  @JoinColumn ( name = "ID_SOLICITUD" ,  referencedColumnName = "ID_SOLICITUD" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_SEGUIMIENTO" , referencedColumnName = "ID_SEGUIMIENTO" , nullable = true,updatable = true, unique = true ) }
	      
	  ) 
	@Singular
	private List<Seguimiento> seguimientos;
}
