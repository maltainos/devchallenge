package com.ibi.challenge.ws.ui.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaisRequest {

	@NotBlank
	@NotNull
	@Size(min = 3, max = 125)
	private String nome;
	
	@NotBlank
	@NotNull
	@Size(min = 3, max = 45)
	private String capital;

	private List<SubRegiaoRequest> subRegioes;
	
	@NotBlank
	@NotNull
	private float area;
}
