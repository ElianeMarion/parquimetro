package br.com.fiap.parquimetro.service.impl;

import br.com.fiap.parquimetro.model.Veiculo;
import br.com.fiap.parquimetro.repository.VeiculoRepository;
import br.com.fiap.parquimetro.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class VeiculoServiceImpl implements VeiculoService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public VeiculoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Veiculo> obterTodos() {
        return this.veiculoRepository.findAll();
    }

    @Override
    public Veiculo buscarPorCodigo(UUID id) {
        return this.veiculoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Veículo não encontrado"));
    }

    @Override
    public Veiculo criar(Veiculo veiculo) {
        return this.veiculoRepository.save(veiculo);
    }
}
