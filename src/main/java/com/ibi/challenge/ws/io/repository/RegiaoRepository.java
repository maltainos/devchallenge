package com.ibi.challenge.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibi.challenge.ws.io.entity.Regiao;

@Repository
public interface RegiaoRepository extends PagingAndSortingRepository<Regiao, Integer>{
	
	public Optional<Regiao> findByRegiaoId(String regiaoId);
	public Optional<Regiao> findByNome(String nome);
}
