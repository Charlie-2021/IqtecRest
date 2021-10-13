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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "TRANSPORTE")
public class Transporte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8701575591751408392L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRANSPORTE")
	private Long id;
	
	@Column(unique = true, name = "NOMBRE", length = 50, nullable = false)
	@NonNull
	@NotNull
	private String nombre;
	
	@Column(name = "CIF", length = 10, nullable = false)
	@NonNull
	@NotNull
	private String cif;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DIRECCION")
	private Direccion direccion;
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	@Singular
	@JoinColumn(name = "ID_CONTACTO", nullable = true)
	private List<Contacto> listaContactos;
}
