package com.ibi.challenge.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ibi.challenge.ws.exception.resource.ResourceNotFoundException;
import com.ibi.challenge.ws.io.entity.Pais;
import com.ibi.challenge.ws.io.entity.Regiao;
import com.ibi.challenge.ws.io.repository.PaisRepository;
import com.ibi.challenge.ws.service.PaisService;
import com.ibi.challenge.ws.shared.ChallengeUtils;
import com.ibi.challenge.ws.shared.dto.PaisDTO;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;

@Service
public class PaisServiceImpl implements PaisService {
	
	@Autowired
	private ChallengeUtils utils;

	@Autowired
	private PaisRepository paisRepository;

	@Override
	public List<PaisDTO> getPaises(int page, int limit, String sortColumn, String sortMode) {

		if (page > 0)
			page = page - 1;
		Sort sort = sortMode.toLowerCase().equals("asc") ? Sort.by(sortColumn).ascending()
				: Sort.by(sortColumn).descending();

		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Pais> pagePais = paisRepository.findAll(pageable);

		List<Pais> paises = pagePais.getContent();

		return listFromEntityToDTO(paises);
	}

	@Override
	public PaisDTO getPais(String paisId) {

		Optional<Pais> searchResult = paisRepository.findByPaisId(paisId);
		if (!searchResult.isPresent())
			throw new ResourceNotFoundException(ResourceNotFoundException.class.toString(), Pais.class.toString());
		return fromEntityToDTO(searchResult.get());

	}

	@Override
	public PaisDTO createPais(PaisDTO paisDTO) {
		
		Pais pais = fromDTOToEntity(paisDTO);
		
		Regiao regiao = new Regiao();
		BeanUtils.copyProperties(paisDTO.getRegiao(), regiao);
		
		pais.setRegiao(regiao);
		
		pais.getRegiao().setId(paisDTO.getRegiao().getId());
		
		pais.setPaisId(utils.generateResourceId(35));
		
		Pais savedPais = paisRepository.save(pais);

		PaisDTO returnValue = fromEntityToDTO(savedPais);
		returnValue.setRegiao(paisDTO.getRegiao());
		
		return returnValue;
	}

	@Override
	public PaisDTO updatePais(PaisDTO paisDTO, String paisId) {

		PaisDTO updatePais = getPais(paisId);
		updatePais.setNome(paisDTO.getNome());
		
		Pais pais = fromDTOToEntity(updatePais);
		pais = paisRepository.save(pais);
		
		return fromEntityToDTO(pais);
	}

	@Override
	public boolean deletePais(String paisId) {

		PaisDTO updateRegiao = getPais(paisId);

		Pais deletePais = fromDTOToEntity(updateRegiao);
		paisRepository.delete(deletePais);

		return true;
	}

	private Pais fromDTOToEntity(PaisDTO paisDTO) {

		Pais returnValue = new Pais();
		BeanUtils.copyProperties(paisDTO, returnValue);

		return returnValue;
	}

	private PaisDTO fromEntityToDTO(Pais pais) {

		PaisDTO returnValue = new PaisDTO();
		BeanUtils.copyProperties(pais, returnValue);

		return returnValue;
	}

	private RegiaoDTO fromEntityToDTO(Regiao regiao) {

		RegiaoDTO returnValue = new RegiaoDTO();
		BeanUtils.copyProperties(regiao, returnValue);
		
		return returnValue;
	}
	
	private List<PaisDTO> listFromEntityToDTO(List<Pais> paises) {

		List<PaisDTO> returnValue = new ArrayList<>();
		
		paises.forEach(pais -> {
			PaisDTO paisDTO = fromEntityToDTO(pais);
			paisDTO.setRegiao(fromEntityToDTO(pais.getRegiao()));
			returnValue.add(paisDTO);		
		});

		return returnValue;
	}

}
