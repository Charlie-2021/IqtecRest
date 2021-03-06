package com.api.iqtec.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "SEGUIMIENTOS")
public class Seguimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350277790011664395L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_SEGUIMIENTO")
	@ApiModelProperty(hidden = true)
	private Long idSeguimiento;
	
	@OneToOne()
	@JoinColumn(name = "ID_ESTADO")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Estado estado;
	
	@Column(name = "FECHA")
	@ApiModelProperty(name = "Fecha",notes = "Inserta la fecha de seguimiento", position = 0 )
	private LocalDateTime fecha;

	@OneToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	
	
	
	
}
