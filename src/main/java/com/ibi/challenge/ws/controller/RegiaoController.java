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

import com.ibi.challenge.ws.service.impl.RegiaoServiceImpl;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;
import com.ibi.challenge.ws.ui.request.RegiaoRequest;
import com.ibi.challenge.ws.ui.response.RegiaoRest;

@RestController
@RequestMapping(path = "/regioes")
public class RegiaoController {

	@Autowired
	private RegiaoServiceImpl regiaoService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public List<RegiaoRest> getRegioes(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "sortColumn", defaultValue = "id") String sortColumn,
			@RequestParam(value = "sortMode", defaultValue = "asc") String sortMode) {

		List<RegiaoDTO> regioesDTO = regiaoService.getRegioes(page, limit, sortColumn, sortMode);

		return listFromDTOtoRest(regioesDTO);
	}

	@GetMapping(path = "/{regiaoId}")
	public ResponseEntity<RegiaoRest> getRegiao(@PathVariable String regiaoId) {
		RegiaoDTO regiaoDTO = regiaoService.getRegiao(regiaoId);
		return ResponseEntity.status(HttpStatus.FOUND).body(fromDTOtoRest(regiaoDTO));
	}

	@PostMapping
	public ResponseEntity<RegiaoRest> createRegiao(@Valid @RequestBody RegiaoRequest regiaoRequest) {

		RegiaoDTO regiaoDTO = fromRequestToDTO(regiaoRequest);
		RegiaoDTO savedRegiaoDTO = regiaoService.createRegiao(regiaoDTO);
		RegiaoRest returnValue = fromDTOtoRest(savedRegiaoDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@PutMapping(path = "/{regiaoId}")
	public ResponseEntity<RegiaoRest> updateRegiao(@Valid @RequestBody RegiaoRequest regiaoRequest, @PathVariable String regiaoId) {
		
		RegiaoDTO regiaoDTO = fromRequestToDTO(regiaoRequest);
		RegiaoDTO updatedRegiao = regiaoService.updateRegiao(regiaoDTO, regiaoId);
		return ResponseEntity.status(HttpStatus.OK).body(fromDTOtoRest(updatedRegiao));
	}
	
	@DeleteMapping(path = "/{regiaoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteRegiao(@PathVariable String regiaoId) {
		regiaoService.deleteRegiao(regiaoId);
	}
	
	private RegiaoDTO fromRequestToDTO(RegiaoRequest regiaoRequest) {

		RegiaoDTO returnValue = new RegiaoDTO();
		BeanUtils.copyProperties(regiaoRequest, returnValue);

		return returnValue;
	}

	private RegiaoRest fromDTOtoRest(RegiaoDTO regiaoDTO) {

		RegiaoRest returnValue = new RegiaoRest();
		BeanUtils.copyProperties(regiaoDTO, returnValue);

		return returnValue;
	}

	private List<RegiaoRest> listFromDTOtoRest(List<RegiaoDTO> regioesDTO) {

		List<RegiaoRest> returnValue = new ArrayList<>();

		for (RegiaoDTO regiaoDTO : regioesDTO)
			returnValue.add(fromDTOtoRest(regiaoDTO));

		return returnValue;
	}

}
