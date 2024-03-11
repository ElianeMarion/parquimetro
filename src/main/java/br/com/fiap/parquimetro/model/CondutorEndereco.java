package br.com.fiap.parquimetro.model;

import br.com.fiap.parquimetro.service.impl.EnderecoServiceImpl;
import lombok.Data;

@Data
public class CondutorEndereco {
    private Condutor condutor;
    private Endereco endereco;
}
