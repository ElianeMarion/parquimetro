package br.com.fiap.parquimetro.service.impl;

import br.com.fiap.parquimetro.model.Endereco;
import br.com.fiap.parquimetro.repository.EnderecoRepository;
import br.com.fiap.parquimetro.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final MongoTemplate mongoTemplate;

    public EnderecoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Endereco buscarPorId(String id) {
        return this.enderecoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Endereço não cadastrado.")
        );
    }

    @Override
    public Endereco cadastrar(Endereco endereco) {
        return this.enderecoRepository.save(endereco);
    }
}
