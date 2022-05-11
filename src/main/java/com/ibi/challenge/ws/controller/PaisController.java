package com.ibi.challenge.ws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ibi.challenge.ws.ui.request.PaisRequest;
import com.ibi.challenge.ws.ui.response.PaisRest;
import com.ibi.challenge.ws.ui.response.RegiaoRest;

@RestController
@RequestMapping(path = "/paises")
public class PaisController {
	
	@Autowired
	private PaisServiceImpl paisService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public List<PaisRest> getRegioes(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "sortColumn", defaultValue = "id") String sortColumn,
			@RequestParam(value = "sortMode", defaultValue = "asc") String sortMode) {

		List<PaisDTO> paisesDTO = paisService.getPaises(page, limit, sortColumn, sortMode);

		List<PaisRest> returnValue = listFromDTOtoRest(paisesDTO);
		
		return returnValue;
	}

	@GetMapping(path = "/{regiaoId}")
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
	
	@DeleteMapping(path = "/{regiaoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePais(@PathVariable String regiaoId) {
		paisService.deletePais(regiaoId);
	}
	
	private PaisDTO fromRequestToDTO(PaisRequest regiaoRequest) {

		PaisDTO returnValue = new PaisDTO();
		BeanUtils.copyProperties(regiaoRequest, returnValue);

		return returnValue;
	}

	private PaisRest fromDTOtoRest(PaisDTO regiaoDTO) {

		PaisRest returnValue = new PaisRest();
		BeanUtils.copyProperties(regiaoDTO, returnValue);

		return returnValue;
	}

	private List<PaisRest> listFromDTOtoRest(List<PaisDTO> paisesDTO) {

		List<PaisRest> returnValue = new ArrayList<>();

		for (PaisDTO paisDTO : paisesDTO) {
			PaisRest paisRest = fromDTOtoRest(paisDTO);
			paisRest.setRegiao(fromDTOtoRest(paisDTO.getRegiao()));
			returnValue.add(paisRest);
		}
		
		return returnValue;
	}

	private RegiaoRest fromDTOtoRest(RegiaoDTO regiaoDTO) {

		RegiaoRest returnValue = new RegiaoRest();
		BeanUtils.copyProperties(regiaoDTO, returnValue);

		return returnValue;
	}

}
