package br.com.fiap.parquimetro.service;

import br.com.fiap.parquimetro.model.Condutor;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CondutorService {

    public List<Condutor> listar();
    public Condutor buscarPorId(String id);

    public Condutor findByNome(String nome);

    public ResponseEntity<String> cadastrar(Condutor condutor);


}
