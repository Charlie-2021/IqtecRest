package com.api.iqtec.modelo;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Contacto {


	private String contactoPrin;

	private String tlfPrin;

	private String movilPrin;
	
	@Email
	private String mailPrin;

	private String contactoSec;

	private String tlfSec;

	private String movilSec;
	
	@Email
	private String mailSec;
}
