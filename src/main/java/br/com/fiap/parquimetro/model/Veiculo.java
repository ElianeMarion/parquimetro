package br.com.fiap.parquimetro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Document
@Data
public class Veiculo {
    @Id
    private String id;

   private String placa;
   private String marca;
   private String modelo;


}
