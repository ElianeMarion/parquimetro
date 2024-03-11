package br.com.fiap.parquimetro.service.impl;

import br.com.fiap.parquimetro.model.Calculo;
import br.com.fiap.parquimetro.model.Condutor;
import br.com.fiap.parquimetro.model.Estacionamento;
import br.com.fiap.parquimetro.model.Veiculo;
import br.com.fiap.parquimetro.repository.CondutorRepository;
import br.com.fiap.parquimetro.repository.EstacionamentoRepository;
import br.com.fiap.parquimetro.repository.VeiculoRepository;
import br.com.fiap.parquimetro.service.EstacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class EstacionamentoServiceImpl implements EstacionamentoService {

    @Autowired
    private MongoTransactionManager transactionManager;

    @Autowired
    private EstacionamentoRepository repository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    public List<Estacionamento> consultarAtivos() {
        return repository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public Estacionamento findById(String id) {
        return this.repository.findById(id).orElseThrow(
                (()-> new IllegalArgumentException("Estacionamento não encontrado"))
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> estacionar(Estacionamento estacionamento) {
        try {
            LocalDateTime dataHoraInicial = LocalDateTime.now();
            if (estacionamento.getCondutor() != null) {
                Condutor motorista = this.condutorRepository.findById(estacionamento.getCondutor().getId()).orElseThrow(
                        () -> new IllegalArgumentException("Condutor não encontrado"));
                estacionamento.setCondutor(motorista);
            } else {
                //tratar o condutor não encontrado
            }
            if (estacionamento.getVeiculo().getId() != null) {
                Veiculo veiculo = this.veiculoRepository.findById(estacionamento.getVeiculo().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));
                estacionamento.setVeiculo(veiculo);
            }
            estacionamento.setHorarioEntrada(dataHoraInicial);
            estacionamento.setAtivo(true);
            this.repository.save(estacionamento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Estacionamento já existe na coleção!");
        }catch (OptimisticLockingFailureException ex) {
            //tratar a exceção - 1º tentar recuperar o documento mais recente no bd
            Estacionamento atualizado = repository.findById(estacionamento.getId())
                    .orElse(null);
            if (atualizado != null) {
                //2º - Atualizar os campos desejados
                atualizado.setCondutor(estacionamento.getCondutor());
                atualizado.setVeiculo(estacionamento.getVeiculo());
                atualizado.setHorarioEntrada(estacionamento.getHorarioEntrada());
                atualizado.setAtivo(true);

                //3º - Incrementar a versão manualmente
                atualizado.setVersion(atualizado.getVersion() + 1);
                this.repository.save(atualizado);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else{
                throw new RuntimeException("Estacionamento não encontrado: " + estacionamento.getId());
            }
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar estacionamento: " + ex.getMessage());

        }
    }
    @Transactional
    @Override
    public ResponseEntity<?> finalizarEstacionamento(String id) {
        try{
            Calculo calculo = new Calculo();
            Estacionamento estacionamento = repository.findById(id).orElseThrow(null);
            if(estacionamento == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro não encontrado");
            }else {
                LocalDateTime horaAtual = LocalDateTime.now();
                estacionamento.setHorarioSaida(horaAtual);
                estacionamento.calcularPeriodo();
                estacionamento.setValor(calculo.calcularTarifa(estacionamento));

                if(estacionamento.isPago()){
                    estacionamento.setPago(true);
                    estacionamento.setAtivo(false);
                }

                this.repository.save(estacionamento);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }catch(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar estacionamento: " + ex.getMessage());

        }

    }

    @Override
    public Page<Estacionamento> findAll(Pageable pageable) {
        Pageable paginacao = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return this.repository.findAll(paginacao);
    }
    @Transactional
   @Override
    public ResponseEntity<?> fiscalizar(@PathVariable String id) {
        try{
            Calculo calculo = new Calculo();
            Estacionamento estacionamento = repository.findById(id).orElseThrow(null);
            if(estacionamento == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro não encontrado");
            }else {
                LocalDateTime horaAtual = LocalDateTime.now();
                if (estacionamento.verificarPeriodoFiscalizacao(horaAtual))
                    return ResponseEntity.status(HttpStatus.OK).body("Dentro do período contratado");
                else
                    return ResponseEntity.status(HttpStatus.OK).body("Fora do período contratado");

            }
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar estacionamento: " + ex.getMessage());

        }

    }
}
