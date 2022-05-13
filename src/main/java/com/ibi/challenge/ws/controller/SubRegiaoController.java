package com.ibi.challenge.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibi.challenge.ws.service.impl.SubRegiaoServiceImpl;
import com.ibi.challenge.ws.shared.dto.SubRegiaoDTO;
import com.ibi.challenge.ws.ui.request.SubRegiaoRequest;
import com.ibi.challenge.ws.ui.response.SubRegiaoRest;

@RestController
@RequestMapping(path = "/sub-regioes")
public class SubRegiaoController {
	
	@Autowired
	private SubRegiaoServiceImpl subRegiaoService;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<SubRegiaoRest> getSubRegioes(@RequestParam(value = "page", defaultValue = "1")int page, @RequestParam(value = "limit", defaultValue = "10")int limit, @RequestParam(value = "sortColumn", defaultValue = "id")String sortColumn, @RequestParam(value = "sortMode", defaultValue = "asc")String sortMode){
		
		List<SubRegiaoDTO> subRegiaoDTO = subRegiaoService.getSubRegioes(page, limit, sortColumn, sortMode);
		List<SubRegiaoRest> returnValue = listFromDTOtoRest(subRegiaoDTO);
		
		return returnValue;
	}
	
	@GetMapping(path = "/{subRegiaoId}")
	public ResponseEntity<SubRegiaoRest> getSubRegiao(@PathVariable String subRegiaoId){
		
		SubRegiaoDTO subRegiaoDTO = subRegiaoService.getSubRegiao(subRegiaoId);
		SubRegiaoRest returnValue = fromDTOtoRest(subRegiaoDTO);
		
		returnValue.add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
						SubRegiaoController.class).getSubRegiao(subRegiaoId)).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.FOUND).body(returnValue);
	}
	
	@PutMapping(path = "/{subRegiaoId}")
	public ResponseEntity<SubRegiaoRest> updateSubRegiao(@RequestBody SubRegiaoRequest subRegiaoRequest, @PathVariable String subRegiaoId){
		
		SubRegiaoDTO subRegiaoDTO = new SubRegiaoDTO();
		BeanUtils.copyProperties(subRegiaoRequest, subRegiaoDTO);
		
		subRegiaoDTO = subRegiaoService.updateSubRegiao(subRegiaoDTO, subRegiaoId);
		
		SubRegiaoRest returnValue = fromDTOtoRest(subRegiaoDTO);
		
		returnValue.add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
						SubRegiaoController.class).getSubRegiao(subRegiaoId)).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@DeleteMapping(path = "/{subRegiaoId}")
	public ResponseEntity<SubRegiaoRest> deleteSubRegiao(@PathVariable String subRegiaoId){
		
		subRegiaoService.deleteSubRegiao(subRegiaoId);
		
		return ResponseEntity.noContent().build();
	}

	private SubRegiaoRest fromDTOtoRest(SubRegiaoDTO subRegiaoDTO) {

		SubRegiaoRest returnValue = new SubRegiaoRest();
		BeanUtils.copyProperties(subRegiaoDTO, returnValue);
		//returnValue.setPaisId(subRegiaoDTO.getPais().getPaisId());
		
		return returnValue;
	}

	private List<SubRegiaoRest> listFromDTOtoRest(List<SubRegiaoDTO> subRegioesDTO) {

		List<SubRegiaoRest> returnValue = new ArrayList<>();

		for (SubRegiaoDTO subRegiaoDTO : subRegioesDTO) {
			returnValue.add(fromDTOtoRest(subRegiaoDTO));
		}

		return returnValue;
	}
}
