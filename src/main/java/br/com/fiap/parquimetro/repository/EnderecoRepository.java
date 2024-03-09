package br.com.fiap.parquimetro.repository;

import br.com.fiap.parquimetro.model.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnderecoRepository extends MongoRepository<Endereco, String> {

}
