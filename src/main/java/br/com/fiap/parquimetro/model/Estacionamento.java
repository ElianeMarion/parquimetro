package br.com.fiap.parquimetro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
@Document
@Data
public class Estacionamento {

    @Id
    private String id;
    @DBRef
    private Veiculo veiculo;
    @DBRef
    private Condutor condutor;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private int duracaoContratada;
    private boolean ativo;
    private BigDecimal valor;
    private boolean pago;
    //Controle de versionamento para transações
    @Version
    private Long version;

    public void   calcularPeriodo() {
        String tempoTotalContratado;
        if (horarioEntrada != null && horarioSaida != null) {
            // Calcular a diferença entre os horários de entrada e saída
            Duration duracao = Duration.between(horarioEntrada, horarioSaida);
            // Converter a diferença para minutos

            long segundos = duracao.toSeconds();
            long horas = segundos / 3600;
            long minutos = (segundos % 3600) / 60;
            long segundosFinais = segundos % 60;
            // Definir a duração contratada
            this.duracaoContratada = (int) minutos;
        }

    }
}
