package br.com.fiap.parquimetro.service;

import br.com.fiap.parquimetro.model.Endereco;

public interface EnderecoService {
    public Endereco buscarPorId(String id);
    public Endereco cadastrar(Endereco endereco);
}
