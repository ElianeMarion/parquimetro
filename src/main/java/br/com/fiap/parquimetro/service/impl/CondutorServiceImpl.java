package br.com.fiap.parquimetro.service.impl;

import br.com.fiap.parquimetro.model.Condutor;
import br.com.fiap.parquimetro.model.Endereco;
import br.com.fiap.parquimetro.model.Veiculo;
import br.com.fiap.parquimetro.repository.CondutorRepository;
import br.com.fiap.parquimetro.repository.EnderecoRepository;
import br.com.fiap.parquimetro.repository.VeiculoRepository;
import br.com.fiap.parquimetro.service.CondutorService;
import br.com.fiap.parquimetro.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorServiceImpl implements CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private EnderecoServiceImpl endereco;

    private final MongoTemplate mongoTemplate;

    public CondutorServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Condutor> listar() {
        return this.condutorRepository.findAll();
    }

    @Override
    public Condutor buscarPorId(String id) {
        return this.condutorRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Condutor não encontrado")
        );
    }

    @Override
    public Condutor findByNome(String nome) {
        return this.condutorRepository.findByNome(nome);

    }

    @Override
    public ResponseEntity<String> cadastrar(Condutor condutor) {
        Endereco end = new Endereco();
        if(condutor.getEndereco().getId() != null){
            end = this.endereco.buscarPorId(condutor.getEndereco().getId());

            if(end.getId() == null)
                end = this.endereco.cadastrar(condutor.getEndereco());
            condutor.setEndereco(end);
            //
        }else{
            condutor.setEndereco(null);
        }
        if(condutor.getVeiculo().getId() != null){
            Veiculo veiculo = this.veiculoRepository.findById(condutor.getVeiculo().getId())
                    .orElseThrow(()-> new IllegalArgumentException("Veículo não encontrado"));
            condutor.setVeiculo(veiculo);
        }else{
            condutor.setVeiculo(null);

        }
        try {
            this.condutorRepository.save(condutor);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar condutor: " + ex.getMessage());
        }
    }

}
