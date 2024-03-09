package br.com.fiap.parquimetro.repository;

import br.com.fiap.parquimetro.model.Condutor;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CondutorRepository extends MongoRepository<Condutor, String> {
    public Condutor findByNome(String nome);
}
