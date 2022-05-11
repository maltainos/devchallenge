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
import com.ibi.challenge.ws.io.entity.Regiao;
import com.ibi.challenge.ws.io.repository.RegiaoRepository;
import com.ibi.challenge.ws.service.RegiaoService;
import com.ibi.challenge.ws.shared.ChallengeUtils;
import com.ibi.challenge.ws.shared.dto.PaisDTO;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;

@Service
public class RegiaoServiceImpl implements RegiaoService {

	@Autowired
	private ChallengeUtils utils;

	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Autowired
	private PaisServiceImpl paisService;

	@Override
	public List<RegiaoDTO> getRegioes(int page, int limit, String sortColumn, String sortMode) {

		if (page > 0)
			page = page - 1;
		Sort sort = sortMode.toLowerCase().equals("asc") ? Sort.by(sortColumn).ascending()
				: Sort.by(sortColumn).descending();

		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Regiao> pageRegiao = regiaoRepository.findAll(pageable);

		List<Regiao> regioes = pageRegiao.getContent();

		return listFromEntityToDTO(regioes);
	}

	@Override
	public RegiaoDTO getRegiao(String regiaoId) {

		Optional<Regiao> searchResult = regiaoRepository.findByRegiaoId(regiaoId);
		if (!searchResult.isPresent())
			throw new ResourceNotFoundException(ResourceNotFoundException.class.toString(), Regiao.class.toString());
		return fromEntityToDTO(searchResult.get());
	}

	@Override
	public RegiaoDTO createRegiao(RegiaoDTO regiaoDTO) {

		Regiao regiao = fromDTOToEntity(regiaoDTO);
		
		regiao.setRegiaoId(utils.generateResourceId(35));
		Regiao savedRegiao = regiaoRepository.save(regiao);

		RegiaoDTO returnValue = fromEntityToDTO(savedRegiao);

		return returnValue;
	}

	@Override
	public RegiaoDTO updateRegiao(RegiaoDTO regiaoDTO, String regiaoId) {
		
		RegiaoDTO updateRegiao = getRegiao(regiaoId);
		updateRegiao.setNome(regiaoDTO.getNome());
		
		Regiao regiao = fromDTOToEntity(updateRegiao);
		regiao = regiaoRepository.save(regiao);
		
		return fromEntityToDTO(regiao);
	}

	@Override
	public boolean deleteRegiao(String regiaoId) {
		
		RegiaoDTO updateRegiao = getRegiao(regiaoId);
		
		Regiao deleteRegiao = fromDTOToEntity(updateRegiao);
		regiaoRepository.delete(deleteRegiao);
		
		return true;
	}

	private Regiao fromDTOToEntity(RegiaoDTO regiaoDTO) {

		Regiao returnValue = new Regiao();
		BeanUtils.copyProperties(regiaoDTO, returnValue);
		
		return returnValue;
	}
	
	private RegiaoDTO fromEntityToDTO(Regiao regiao) {

		RegiaoDTO returnValue = new RegiaoDTO();
		BeanUtils.copyProperties(regiao, returnValue);
		
		return returnValue;
	}

	private List<RegiaoDTO> listFromEntityToDTO(List<Regiao> regioes) {

		List<RegiaoDTO> returnValue = new ArrayList<>();
		regioes.forEach(regiao -> returnValue.add(fromEntityToDTO(regiao)));

		return returnValue;
	}

	@Override
	public PaisDTO createPais(PaisDTO paisDTO, String regiaoId) {
		
		RegiaoDTO regiaoDTO = getRegiao(regiaoId);
		paisDTO.setRegiao(regiaoDTO);
		
		PaisDTO returnValue = paisService.createPais(paisDTO);
		System.out.println(returnValue);
		return returnValue;
	}

}
