package br.com.fiap.parquimetro.controller;

import br.com.fiap.parquimetro.model.Condutor;
import br.com.fiap.parquimetro.model.CondutorEndereco;
import br.com.fiap.parquimetro.model.Endereco;
import br.com.fiap.parquimetro.service.CondutorService;
import br.com.fiap.parquimetro.service.impl.CondutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/condutores")
public class CondutorController {

    @Autowired
    private CondutorServiceImpl condutorService;

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailureException(
            OptimisticLockingFailureException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Erro de concorrência: O encerramento do estacionamento foi atualizado por outro usuário. " +
                        "Por favor, tente novamente!");
    }

    @GetMapping
    public List<Condutor> listar() {
        return condutorService.listar();
    }

    @GetMapping("/{id}")
    public Condutor buscarPorId(@PathVariable  String id) {
        return this.condutorService.buscarPorId(id);
    }

    @GetMapping("/busca-nome")
    public Condutor findByNome(@RequestParam("nome") String nome) {
        return this.condutorService.findByNome(nome);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody CondutorEndereco request) {
        Condutor condutor = request.getCondutor();
        Endereco endereco = request.getEndereco();
        return this.condutorService.cadastrarCondutorComEndereco(condutor, endereco);
    }
}
