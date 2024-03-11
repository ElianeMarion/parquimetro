package br.com.fiap.parquimetro.controller;

import br.com.fiap.parquimetro.model.Veiculo;
import br.com.fiap.parquimetro.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> obterTodos() {
        return this.veiculoService.obterTodos();
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorCodigo(@PathVariable String id) {
        return this.veiculoService.buscarPorCodigo(id);
    }

    @PostMapping
    public Veiculo criar(@RequestBody  Veiculo veiculo){
        return this.veiculoService.criar(veiculo);
    }



}
