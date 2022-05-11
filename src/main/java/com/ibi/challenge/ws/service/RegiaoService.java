package com.ibi.challenge.ws.service;

import java.util.List;

import com.ibi.challenge.ws.shared.dto.PaisDTO;
import com.ibi.challenge.ws.shared.dto.RegiaoDTO;

public interface RegiaoService {

	public List<RegiaoDTO> getRegioes(int page, int limit, String sortColumn, String sortMode);
	public RegiaoDTO getRegiao(String regiaoId);
	public RegiaoDTO createRegiao(RegiaoDTO regiaoDTO);
	public RegiaoDTO updateRegiao(RegiaoDTO regiaoDTO, String regiaoId);
	public boolean deleteRegiao(String regiaoId);
	public PaisDTO createPais(PaisDTO paisDTO, String regiaoId);
}
