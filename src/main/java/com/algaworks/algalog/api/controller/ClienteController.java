package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.dto.ClienteDTO;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.validation.Valid;
import java.lang.reflect.Type;
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

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/listar")
    @ApiOperation(value = "Retorna Lista Clientes")
    public List<ClienteDTO> listar() {
        var clientes = clienteService.buscarTodos();
        Type listType = new TypeToken<List<ClienteDTO>>(){}.getType();
        return modelMapper.map(clientes, listType);
    }

    @GetMapping("/nome/{clienteNome}")
    @ApiOperation(value = "Retorna Lista Clientes / Busca por nome")
    public List<ClienteDTO> nome(@PathVariable String clienteNome) {
        var clientes = clienteService.buscarNome(clienteNome);
        Type listType = new TypeToken<List<ClienteDTO>>(){}.getType();
        return modelMapper.map(clientes, listType);
    }

    @GetMapping("/contem/{parteNomeCliente}")
    @ApiOperation(value = "Retorna Lista Clientes / Busca parte nome")
    public List<ClienteDTO> contem(@PathVariable String parteNomeCliente) {
        var clientes = clienteService.findByNomeContaining(parteNomeCliente);
        Type listType = new TypeToken<List<ClienteDTO>>(){}.getType();
        return modelMapper.map(clientes, listType);
    }

    @GetMapping("/{clienteId}")
    @ApiOperation(value = "Retorna Lista Clientes")
    public ResponseEntity<Cliente> porId(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Retorna um Cliente / Adiciona um Cliente")
    public ClienteDTO adicionar(@Valid @RequestBody Cliente cliente) {
        var clienteRetorno = clienteService.salvar(cliente);

        return modelMapper.map(clienteRetorno, ClienteDTO.class);
    }

    @PutMapping("/atualizar/{clienteId}")
    @ApiOperation(value = "Retorna um Cliente / Atualiza um Cliente")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long clienteId, @RequestBody Cliente cliente) {

        if (!clienteService.existsById(clienteId)) {
            throw new NegocioException("Cliente: [" + clienteId + "] não localizado!");
        }

        cliente.setId(clienteId);
        var clienteRetorno = clienteService.salvar(cliente);

        return ResponseEntity.ok(modelMapper.map(clienteRetorno, ClienteDTO.class));
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
