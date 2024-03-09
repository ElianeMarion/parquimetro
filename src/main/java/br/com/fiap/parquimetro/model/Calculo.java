package br.com.fiap.parquimetro.model;

import java.math.BigDecimal;

public class Calculo  implements CalculoEstacionamento{

    @Override
    public BigDecimal calcularTarifa(Estacionamento estacionamento) {
        if (estacionamento.getDuracaoContratada() <= 60 )
            return TARIFA;
        else
            return CalculoEstacionamento.TARIFA.multiply((new BigDecimal(estacionamento.getDuracaoContratada()/60)));
    }
}
