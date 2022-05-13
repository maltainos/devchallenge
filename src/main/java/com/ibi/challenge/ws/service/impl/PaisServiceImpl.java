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

import com.ibi.challenge.ws.exception.resource.ResourceExistException;
import com.ibi.challenge.ws.exception.resource.ResourceNotFoundException;
import com.ibi.challenge.ws.io.entity.Pais;
import com.ibi.challenge.ws.io.entity.Regiao;
import com.ibi.challenge.ws.io.entity.SubRegiao;
import com.ibi.challenge.ws.io.repository.PaisRepository;
import com.ibi.challenge.ws.service.PaisService;
import com.ibi.challenge.ws.shared.ChallengeUtils;
import com.ibi.challenge.ws.shared.dto.PaisDTO;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;
import com.ibi.challenge.ws.shared.dto.SubRegiaoDTO;

@Service
public class PaisServiceImpl implements PaisService {
	
	@Autowired
	private ChallengeUtils utils;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private SubRegiaoServiceImpl subRegiaoService;
	
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
		
		if(paisRepository.findByNome(paisDTO.getNome()).isPresent())
			throw new ResourceExistException(paisDTO.getNome()+" já existe");
		
		if(paisRepository.findByCapital(paisDTO.getCapital()).isPresent())
			throw new ResourceExistException(paisDTO.getCapital()+" já existe");
		
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
		updatePais.setCapital(paisDTO.getCapital());
		updatePais.setArea(paisDTO.getArea());
		
		Pais pais = fromDTOToEntity(updatePais);
		
		System.out.println(updatePais);
		
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
	
	@Override
	public SubRegiaoDTO createSubRegiao(SubRegiaoDTO subRegiaoDTO, String paisId) {
		PaisDTO paisDTO = getPais(paisId);
		subRegiaoDTO.setPais(paisDTO);
		
		SubRegiaoDTO returnValue = subRegiaoService.createSubRegiao(subRegiaoDTO);
		return returnValue;
	}

	private Pais fromDTOToEntity(PaisDTO paisDTO) {

		Pais returnValue = new Pais();
		BeanUtils.copyProperties(paisDTO, returnValue);
		
		Regiao regiao = new Regiao();
		BeanUtils.copyProperties(paisDTO.getRegiao(), regiao);
		returnValue.setRegiao(regiao);

		return returnValue;
	}

	private PaisDTO fromEntityToDTO(Pais pais) {

		PaisDTO returnValue = new PaisDTO();
		BeanUtils.copyProperties(pais, returnValue);
		
		returnValue.setRegiao(fromEntityToDTO(pais.getRegiao()));
		if(pais.getSubRegioes() != null )
			returnValue.setSubRegioes(listFromEntityToDTOInSubRegiao(pais.getSubRegioes()));

		return returnValue;
	}
	

	private RegiaoDTO fromEntityToDTO(Regiao regiao) {

		RegiaoDTO returnValue = new RegiaoDTO();
		BeanUtils.copyProperties(regiao, returnValue);
		
		return returnValue;
	}
	
	private List<SubRegiaoDTO> listFromEntityToDTOInSubRegiao(List<SubRegiao> subRegioes) {

		List<SubRegiaoDTO> returnValue = new ArrayList<>();
		
		subRegioes.forEach(subRegiao -> {
			
			SubRegiaoDTO subRegiaoDTO = new SubRegiaoDTO();
			BeanUtils.copyProperties(subRegiao, subRegiaoDTO);
			returnValue.add(subRegiaoDTO);		
		});

		return returnValue;
	}
	
	private List<PaisDTO> listFromEntityToDTO(List<Pais> paises) {

		List<PaisDTO> returnValue = new ArrayList<>();
		
		paises.forEach(pais -> {
			PaisDTO paisDTO = fromEntityToDTO(pais);
			paisDTO.setSubRegioes(listFromEntityToDTOInSubRegiao(pais.getSubRegioes()));
			returnValue.add(paisDTO);		
		});

		return returnValue;
	}

}
