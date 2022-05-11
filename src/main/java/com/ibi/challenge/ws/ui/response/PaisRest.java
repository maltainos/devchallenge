package com.ibi.challenge.ws.ui.response;

import java.util.List;

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
public class PaisRest {

	private String paisId;
	private String nome;
	private String capital;
	private RegiaoRest regiao;
	private List<SubRegiaoRest> subRegioes;
	private float area;
}
