package br.com.fiap.parquimetro.repository;

import br.com.fiap.parquimetro.model.Estacionamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstacionamentoRepository extends MongoRepository<Estacionamento, String> {
    Page<Estacionamento> findAll(Pageable paginacao);

    List<Estacionamento> findByAtivoTrue();
}
