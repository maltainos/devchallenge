package com.ibi.challenge.ws.ui.response;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

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
@Relation(collectionRelation = "subRegioes", itemRelation = "subRegiao")
public class SubRegiaoRest extends RepresentationModel<SubRegiaoRest>{
	
	private String subRegiaoId;
	private String nome;
}
