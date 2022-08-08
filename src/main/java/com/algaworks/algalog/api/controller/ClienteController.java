package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@Api(value = "API REST Clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    @ApiOperation(value = "Retorna Lista Clientes")
    public List<Cliente> listar() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/nome/{clienteNome}")
    @ApiOperation(value = "Retorna Lista Clientes / Busca por nome")
    public List<Cliente> nome(@PathVariable String clienteNome) {
        return clienteService.buscarNome(clienteNome);
    }

    @GetMapping("/contem/{parteNomeCliente}")
    @ApiOperation(value = "Retorna Lista Clientes / Busca parte nome")
    public List<Cliente> contem(@PathVariable String parteNomeCliente) {
        return clienteRepository.findByNomeContaining(parteNomeCliente);
    }

    @GetMapping("/{clienteId}")
    @ApiOperation(value = "Retorna Lista Clientes")
    public ResponseEntity<Cliente> porId(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                //.map(cliente -> ResponseEntity.ok(cliente)) mesma coisa que debaixo
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Retorna um Cliente / Adiciona um Cliente")
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @PutMapping("/atualizar/{clienteId}")
    @ApiOperation(value = "Retorna um Cliente / Atualiza um Cliente")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody Cliente cliente) {

        if (!clienteService.existsById(clienteId)) {
            throw new NegocioException("Cliente: [" + clienteId + "] não localizado!");
        }

        cliente.setId(clienteId);
        var clienteRetorno = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteRetorno);
    }

    @DeleteMapping("/excluir/{clienteId}")
    @ApiOperation(value = "Apaga um Cliente")
    public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {
        if (!clienteService.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        clienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }
}
