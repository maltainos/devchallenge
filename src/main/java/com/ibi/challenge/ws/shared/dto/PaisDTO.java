package com.ibi.challenge.ws.shared.dto;

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
public class PaisDTO {
	
	private Integer id;
	private String paisId;
	private String nome;
	private String capital;
	private RegiaoDTO regiao;
	private List<SubRegiaoDTO> subRegioes;
	private float area;
}
