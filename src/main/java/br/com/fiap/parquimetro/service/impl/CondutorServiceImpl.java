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
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class CondutorServiceImpl implements CondutorService {

    @Autowired
    private MongoTransactionManager transactionManager;

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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
            end = this.enderecoRepository.findById(condutor.getEndereco().getId()).orElse(null);

            if(end.getId() == null)
                end = this.enderecoRepository.save(condutor.getEndereco());
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

    @Override
    public ResponseEntity<String> cadastrarCondutorComEndereco(Condutor condutor, Endereco endereco) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((status -> {
            try{
                enderecoRepository.save(endereco);
                condutor.setEndereco(endereco);
                condutorRepository.save(condutor);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }catch (Exception e){
                //tratar erro e lançar a transaçao de volta
                status.setRollbackOnly(); //desfazer tudo o que foi feito antes de lançar exceção

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao cadastrar condutor: " + e.getMessage());
            }

        }));
        return null;
    }

}
