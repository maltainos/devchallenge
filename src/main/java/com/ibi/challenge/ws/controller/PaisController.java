package com.ibi.challenge.ws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibi.challenge.ws.service.impl.PaisServiceImpl;
import com.ibi.challenge.ws.shared.dto.PaisDTO;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;
import com.ibi.challenge.ws.shared.dto.SubRegiaoDTO;
import com.ibi.challenge.ws.ui.request.PaisRequest;
import com.ibi.challenge.ws.ui.request.SubRegiaoRequest;
import com.ibi.challenge.ws.ui.response.PaisRest;
import com.ibi.challenge.ws.ui.response.RegiaoRest;
import com.ibi.challenge.ws.ui.response.SubRegiaoRest;

@RestController
@RequestMapping(path = "/paises")
public class PaisController {
	
	@Autowired
	private PaisServiceImpl paisService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public CollectionModel<PaisRest> getRegioes(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "sortColumn", defaultValue = "id") String sortColumn,
			@RequestParam(value = "sortMode", defaultValue = "asc") String sortMode) {

		List<PaisDTO> paisesDTO = paisService.getPaises(page, limit, sortColumn, sortMode);

		List<PaisRest> returnValue = listFromDTOtoRest(paisesDTO);
		
		CollectionModel<PaisRest> collectionReturnValue = CollectionModel.of(returnValue);
		
		collectionReturnValue.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaisController.class).getRegioes(page, limit, sortColumn, sortMode)).withRel(IanaLinkRelations.COLLECTION));
		
		return collectionReturnValue;
	}

	@GetMapping(path = "/{paisId}")
	public ResponseEntity<PaisRest> getPais(@PathVariable String paisId) {
		
		PaisDTO paisDTO = paisService.getPais(paisId);
		 
		return ResponseEntity.status(HttpStatus.FOUND).body(fromDTOtoRest(paisDTO));
	}

	@PostMapping
	public ResponseEntity<PaisRest> createPais(@Valid @RequestBody PaisRequest paisRequest) {

		PaisDTO paisDTO = fromRequestToDTO(paisRequest);
		PaisDTO savedPaisDTO = paisService.createPais(paisDTO);
		PaisRest returnValue = fromDTOtoRest(savedPaisDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@PutMapping(path = "/{paisId}")
	public ResponseEntity<PaisRest> updatePais(@Valid @RequestBody PaisRequest regiaoRequest, @PathVariable String paisId) {
		
		PaisDTO paisDTO = fromRequestToDTO(regiaoRequest);
		PaisDTO updatePais = paisService.updatePais(paisDTO, paisId);
		return ResponseEntity.status(HttpStatus.OK).body(fromDTOtoRest(updatePais));
	}
	
	@DeleteMapping(path = "/{paisId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePais(@PathVariable String paisId) {
		paisService.deletePais(paisId);
	}
	
	@PostMapping(path = "/{paisId}/sub-regioes")
	public ResponseEntity<SubRegiaoRest> createSubRegiao(@Valid @RequestBody SubRegiaoRequest subRegiaoRequest, @PathVariable String paisId){
		
		SubRegiaoDTO subRegiaoDTO = new SubRegiaoDTO();
		BeanUtils.copyProperties(subRegiaoRequest, subRegiaoDTO);
		
		SubRegiaoDTO createdSubRegiaoDTO = paisService.createSubRegiao(subRegiaoDTO, paisId);
		
		SubRegiaoRest returnValue = new SubRegiaoRest();
		BeanUtils.copyProperties(createdSubRegiaoDTO, returnValue);
		//returnValue.setPaisId(createdSubRegiaoDTO.getPais().getPaisId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	private PaisDTO fromRequestToDTO(PaisRequest regiaoRequest) {

		PaisDTO returnValue = new PaisDTO();
		BeanUtils.copyProperties(regiaoRequest, returnValue);

		return returnValue;
	}

	private PaisRest fromDTOtoRest(PaisDTO paisDTO) {

		PaisRest returnValue = new PaisRest();
		BeanUtils.copyProperties(paisDTO, returnValue);
		
		System.out.println(paisDTO);
		
		returnValue.setRegiao(fromDTOtoRest(paisDTO.getRegiao()));
		
		returnValue.setSubRegioes(listFromDTOtoRestInSubRegiao(paisDTO.getSubRegioes()));
		

		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PaisController.class)
						.getPais(returnValue.getPaisId()))
				.withSelfRel());

		return returnValue;
	}
	
	private List<SubRegiaoRest> listFromDTOtoRestInSubRegiao(List<SubRegiaoDTO> subRegioesDTO) {

		List<SubRegiaoRest> returnValue = new ArrayList<>();

		for (SubRegiaoDTO subRegiaoDTO : subRegioesDTO) {
			SubRegiaoRest addValue = new SubRegiaoRest();
			BeanUtils.copyProperties(subRegiaoDTO, addValue);
			
			addValue.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(
							SubRegiaoController.class).getSubRegiao(addValue.getSubRegiaoId())).withSelfRel());

			returnValue.add(addValue);
		}
		
		return returnValue;
	}

	private List<PaisRest> listFromDTOtoRest(List<PaisDTO> paisesDTO) {

		List<PaisRest> returnValue = new ArrayList<>();

		for (PaisDTO paisDTO : paisesDTO) {
			PaisRest paisRest = fromDTOtoRest(paisDTO);
			//paisRest.setSubRegioes(listFromDTOtoRestInSubRegiao(paisDTO.getSubRegioes()));
			returnValue.add(paisRest);
		}
		
		return returnValue;
	}

	private RegiaoRest fromDTOtoRest(RegiaoDTO regiaoDTO) {

		RegiaoRest returnValue = new RegiaoRest();
		BeanUtils.copyProperties(regiaoDTO, returnValue);
		
		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RegiaoController.class).getRegiao(returnValue.getRegiaoId()))
				.withSelfRel());

		return returnValue;
	}

}
