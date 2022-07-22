package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CatalogoClienteService catalogoClienteService;

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/nome")
    public List<Cliente> nome() {
        return clienteRepository.findByNome("Suzana");
    }

    @GetMapping("/contem")
    public List<Cliente> contem() {
        return clienteRepository.findByNomeContaining("nn");
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> porId(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                //.map(cliente -> ResponseEntity.ok(cliente)) mesma coisa que debaixo
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/atualizar/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        var clienteRetorno = new Cliente();
        clienteRetorno.setId(clienteId);
        clienteRetorno = catalogoClienteService.salvar(cliente);

        return ResponseEntity.ok(clienteRetorno);
    }

    @DeleteMapping("/excluir/{clienteId}")
    public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }
}
