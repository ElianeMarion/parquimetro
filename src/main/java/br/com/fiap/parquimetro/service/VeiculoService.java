package br.com.fiap.parquimetro.service;

import br.com.fiap.parquimetro.model.Veiculo;

import java.util.List;
import java.util.UUID;

public interface VeiculoService {
    public List<Veiculo> obterTodos();
    public Veiculo buscarPorCodigo(String id);
    public Veiculo criar(Veiculo veiculo);

}
