package com.ibi.challenge.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibi.challenge.ws.io.entity.SubRegiao;

@Repository
public interface SubRegiaoRepository extends PagingAndSortingRepository<SubRegiao, Integer>{

	public Optional<SubRegiao> findBySubRegiaoId(String subRegiaoId);

}
