package br.com.fiap.parquimetro.model;

import java.math.BigDecimal;

public interface CalculoEstacionamento {
    public final BigDecimal TARIFA = new BigDecimal(4.5);
    public BigDecimal calcularTarifa (Estacionamento estacionamento);
}
