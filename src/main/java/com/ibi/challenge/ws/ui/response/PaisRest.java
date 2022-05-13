package com.ibi.challenge.ws.ui.response;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

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
@EqualsAndHashCode(callSuper = false)
@ToString
public class PaisRest extends RepresentationModel<PaisRest>{

	private String paisId;
	private String nome;
	private String capital;
	private RegiaoRest regiao;
	private List<SubRegiaoRest> subRegioes;
	private float area;
}
