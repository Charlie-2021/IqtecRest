package com.api.iqtec.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "CONTACTOS")
public class Contacto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -751799804447077424L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID_CONTACTO")
	private Long idContacto;
	
	@NonNull
	@NotNull
	@Column(name = "NOMBRE", length = 50, nullable = false)
	private String nombre;

	@Column(name = "TELEFONO_PRINCIPAL", length = 25, nullable = false)
	@NonNull
	@NotNull
	private String telefono1;

	@Column(name = "TELEFONO_SECUNDARIO", length = 25)
	private String telefono2;
	
	@Email
	@Column(name = "EMAIL", length = 50)
	private String email;

}
