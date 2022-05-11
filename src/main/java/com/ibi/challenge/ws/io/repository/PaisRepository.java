package com.ibi.challenge.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibi.challenge.ws.io.entity.Pais;

@Repository
public interface PaisRepository extends PagingAndSortingRepository<Pais, Integer>{

	public Optional<Pais> findByPaisId(String paisId);

}
