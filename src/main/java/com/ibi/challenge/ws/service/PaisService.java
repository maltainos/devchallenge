package com.ibi.challenge.ws.service;

import java.util.List;

import com.ibi.challenge.ws.shared.dto.PaisDTO;

public interface PaisService {

	public List<PaisDTO> getPaises(int page, int limit, String sortColumn, String sortMode);
	public PaisDTO getPais(String paisId);
	public PaisDTO createPais(PaisDTO paisDTO);
	public PaisDTO updatePais(PaisDTO paisDTO, String paisId);
	public boolean deletePais(String paisId);
}
