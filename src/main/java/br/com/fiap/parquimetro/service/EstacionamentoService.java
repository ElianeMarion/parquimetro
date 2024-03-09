package br.com.fiap.parquimetro.service;

import br.com.fiap.parquimetro.model.Estacionamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EstacionamentoService {
    public Page<Estacionamento> findAll(Pageable paginacao);
    public List<Estacionamento> consultarAtivos();

    public Estacionamento findById(String id);

    public ResponseEntity<?> estacionar(Estacionamento estacionamento);

    public ResponseEntity<?> finalizarEstacionamento(String id);


}
