package br.com.fiap.parquimetro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;
@Document
@Data
public class Condutor {

    @Id
    private String id;
    private String nome;
    @DBRef
    private Endereco endereco;
    private String telefone;
    private String email;
    private FormaDePagamento formaDePagamento;
    @DBRef
    private Veiculo veiculo;

}
