package br.com.fiap.parquimetro.service;

import br.com.fiap.parquimetro.model.Endereco;

import java.util.List;

public interface EnderecoService {
    public Endereco buscarPorId(String id);

    public List<Endereco> listar();
    public Endereco cadastrar(Endereco endereco);
}
