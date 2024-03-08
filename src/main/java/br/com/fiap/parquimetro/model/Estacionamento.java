package br.com.fiap.parquimetro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Document
@Data
public class Estacionamento {

    @Id
    private UUID id;
    @DBRef
    private Veiculo veiculo;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private int duracaoContratada;
    private boolean ativo;
    private BigDecimal valor;
    private boolean pago;
    //Controle de versionamento para transações
    @Version
    private Long version;

}
