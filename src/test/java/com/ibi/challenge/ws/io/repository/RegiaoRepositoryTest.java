package com.ibi.challenge.ws.io.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ibi.challenge.ws.io.entity.Regiao;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RegiaoRepositoryTest {
	
	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Test
	public void createRegiaoTest() {
		
		Regiao regiao = new Regiao(null, "testId", "RegiaoTest", new ArrayList<>());
		Regiao savedRegiao = regiaoRepository.save(regiao);
		
		System.out.println(savedRegiao);
		assertThat(savedRegiao).isNotNull();
	}
}
