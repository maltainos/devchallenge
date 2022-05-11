package com.ibi.challenge.ws.service;

import java.util.List;

import com.ibi.challenge.ws.shared.dto.SubRegiaoDTO;

public interface SubRegiaoService {
	
	public List<SubRegiaoDTO> getSubRegioes(int page, int limit, String sortColumn, String sortMode);

	public SubRegiaoDTO getSubRegiao(String subRegiaoId);

	public SubRegiaoDTO createSubRegiao(SubRegiaoDTO subRegiaoDTO);

	public SubRegiaoDTO updateSubRegiao(SubRegiaoDTO subRegiaoDTO, String subRegiaoId);

	public boolean deleteSubRegiao(String regiaoId);
}
