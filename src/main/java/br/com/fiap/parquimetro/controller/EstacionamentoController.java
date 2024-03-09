package br.com.fiap.parquimetro.controller;

import br.com.fiap.parquimetro.model.Estacionamento;
import br.com.fiap.parquimetro.service.impl.EstacionamentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/estacionados")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoServiceImpl estacionamentoService;

    @GetMapping
    public Page<Estacionamento> findAll(Pageable pageable){
        return this.estacionamentoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Estacionamento findById(@PathVariable String id) {
        return this.estacionamentoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> estacionar(@RequestBody Estacionamento estacionamento) {
        return this.estacionamentoService.estacionar(estacionamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> finalizarEstacionamento(@PathVariable String id) {
        return this.estacionamentoService.finalizarEstacionamento(id);
    }
}
