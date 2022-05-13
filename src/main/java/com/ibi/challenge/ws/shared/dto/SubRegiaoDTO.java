package com.ibi.challenge.ws.shared.dto;

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
public class SubRegiaoDTO {
	
	private Integer id;
	private String subRegiaoId;
	private String nome;
	private PaisDTO pais;
}
