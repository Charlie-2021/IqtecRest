package com.api.iqtec.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.api.iqtec.modelo.enums.NombreEstado;

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
	private Long idSolicitud;
	
	
	@NonNull
	@NotNull
	@Column(name = "REFERENCIA", length = 50, unique = true)
	private String referencia;
	
	
	@Column(name = "FECHA_RECOGIDA")
	private String fechaRecogida;
	
	@Column(name = "HORARIO", length = 50)
	private String horario;
	
	@Column(name = "COMENTARIO_TRANSPORTE")
	private String comentTransporte;
	
	
	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "MATERIAL_SOLICITUD" , 
	      joinColumns = {  @JoinColumn ( name = "ID_SOLICITUD" ,  referencedColumnName = "ID_SOLICITUD" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_MATERIAL" , referencedColumnName = "ID_MATERIAL" ,  unique = true ) }
	      
	  ) 
	@Singular
	private Set<Material> materiales;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SEDE")
	@NotNull
	private Sede sede;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ID_TRANSPORTE")
	@NotNull
	private Transporte transporte;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "ID_INSTRUCCIONES")
	private Instrucciones instrucciones;
	

	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable 
	  ( 
	      name = "SEGUIMIENTO_SOLICITUD" , 
	      joinColumns = {  @JoinColumn ( name = "ID_SOLICITUD" ,  referencedColumnName = "ID_SOLICITUD" )  }, 
	      inverseJoinColumns = {  @JoinColumn ( name = "ID_SEGUIMIENTO" , referencedColumnName = "ID_SEGUIMIENTO" ,  unique = true ) }
	      
	  ) 
	@Singular
	private Set<Seguimiento> seguimientos;
}
