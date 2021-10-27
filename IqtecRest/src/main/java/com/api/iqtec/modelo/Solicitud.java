package com.api.iqtec.modelo;

import java.io.Serializable;
import java.time.LocalDate;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "SOLICUTUD")
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
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@OneToOne( cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	@JoinColumn(name = "ID_MATERIAL")
	@NotNull
	private Material material;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@NotNull
	private Sede sede;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ID_TRANSPORTE")
	@NotNull
	private Transporte transporte;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "ID_INSTRUCCIONES")
	private Instrucciones instrucciones;
	

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "ID_SEGUIMIENTO")
	private Seguimiento seguimiento;
}
