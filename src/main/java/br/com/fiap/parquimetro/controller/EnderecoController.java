package br.com.fiap.parquimetro.controller;

import br.com.fiap.parquimetro.model.Endereco;
import br.com.fiap.parquimetro.service.impl.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoServiceImpl enderecoService;

    @GetMapping
    public List<Endereco> listar(){
        return this.enderecoService.listar();
    }

    @GetMapping("/{id}")
    public Endereco buscarPorId(String id){
        return this.enderecoService.buscarPorId(id);
    }

    @PostMapping
    public Endereco cadastrar(@RequestBody  Endereco endereco){
        return this.enderecoService.cadastrar(endereco);
    }


}
