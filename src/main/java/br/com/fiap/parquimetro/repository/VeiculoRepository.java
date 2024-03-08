package br.com.fiap.parquimetro.repository;

import br.com.fiap.parquimetro.model.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface VeiculoRepository extends MongoRepository<Veiculo, UUID> {
}
