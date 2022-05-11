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
import com.ibi.challenge.ws.io.entity.SubRegiao;
import com.ibi.challenge.ws.io.repository.SubRegiaoRepository;
import com.ibi.challenge.ws.service.SubRegiaoService;
import com.ibi.challenge.ws.shared.ChallengeUtils;
import com.ibi.challenge.ws.shared.dto.SubRegiaoDTO;

@Service
public class SubRegiaoServiceImpl implements SubRegiaoService {

	@Autowired
	private ChallengeUtils utils;

	@Autowired
	private SubRegiaoRepository subRegiaoRepository;

	@Override
	public List<SubRegiaoDTO> getSubRegioes(int page, int limit, String sortColumn, String sortMode) {

		if (page > 0)
			page -= 1;

		Sort sort = sortMode.toLowerCase().equals("asc") ? Sort.by(sortColumn.toLowerCase()).ascending()
				: Sort.by(sortColumn.toLowerCase()).descending();
		Pageable pageabale = PageRequest.of(page, limit, sort);

		Page<SubRegiao> subRegiaoPerPege = subRegiaoRepository.findAll(pageabale);

		List<SubRegiao> subRegioes = subRegiaoPerPege.getContent();

		return listFromEntityToDTO(subRegioes);
	}

	@Override
	public SubRegiaoDTO getSubRegiao(String subRegiaoId) {

		Optional<SubRegiao> searchResult = subRegiaoRepository.findBySubRegiaoId(subRegiaoId);
		if (!searchResult.isPresent())
			throw new ResourceNotFoundException(ResourceNotFoundException.class.toString(), SubRegiao.class.toString());
		return fromEntityToDTO(searchResult.get());
	}

	@Override
	public SubRegiaoDTO createSubRegiao(SubRegiaoDTO subRegiaoDTO) {
		
		SubRegiao subRegiao = fromDTOToEntity(subRegiaoDTO);

		subRegiao.setSubRegiaoId(utils.generateResourceId(35));
		SubRegiao savedRegiao = subRegiaoRepository.save(subRegiao);

		SubRegiaoDTO returnValue = fromEntityToDTO(savedRegiao);

		return returnValue;
	}

	@Override
	public SubRegiaoDTO updateSubRegiao(SubRegiaoDTO subRegiaoDTO, String subRegiaoId) {

		SubRegiaoDTO updateSubRegiao = getSubRegiao(subRegiaoId);
		updateSubRegiao.setNome(subRegiaoDTO.getNome());

		SubRegiao subRegiao = fromDTOToEntity(updateSubRegiao);
		subRegiao = subRegiaoRepository.save(subRegiao);

		return fromEntityToDTO(subRegiao);
	}

	@Override
	public boolean deleteSubRegiao(String subRegiaoId) {

		SubRegiaoDTO updateSubRegiao = getSubRegiao(subRegiaoId);

		SubRegiao deleteSubRegiao = fromDTOToEntity(updateSubRegiao);
		subRegiaoRepository.delete(deleteSubRegiao);

		return true;
	}

	private SubRegiao fromDTOToEntity(SubRegiaoDTO subRegiaoDTO) {

		SubRegiao returnValue = new SubRegiao();
		BeanUtils.copyProperties(subRegiaoDTO, returnValue);

		return returnValue;
	}

	private SubRegiaoDTO fromEntityToDTO(SubRegiao subRegiao) {

		SubRegiaoDTO returnValue = new SubRegiaoDTO();
		BeanUtils.copyProperties(subRegiao, returnValue);

		return returnValue;
	}

	private List<SubRegiaoDTO> listFromEntityToDTO(List<SubRegiao> subRegioes) {

		List<SubRegiaoDTO> returnValue = new ArrayList<>();
		subRegioes.forEach(subRegiao -> returnValue.add(fromEntityToDTO(subRegiao)));

		return returnValue;
	}

}
