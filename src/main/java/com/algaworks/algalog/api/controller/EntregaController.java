package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.dto.EntregaDTO;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaReository;
import com.algaworks.algalog.domain.service.EntregaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaReository entregaReository;
    @Autowired
    private EntregaService entregaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/solicitar")
    @ResponseStatus(HttpStatus.CREATED)
    public Entrega solicitarEntrega(@Valid @RequestBody Entrega entrega) {
        return entregaService.solicitarEntrega(entrega);
    }

    @GetMapping("/listar")
    public List<Entrega> listar() {
        return entregaReository.findAll();
    }

    @GetMapping("/listar/{entregaId}")
    public ResponseEntity<EntregaDTO> buscar(@PathVariable Long entregaId) {
        return entregaReository.findById(entregaId)
                .map(entrega -> {
                    var entregaDTO = modelMapper.map(entrega, EntregaDTO.class);

                    return ResponseEntity.ok(entregaDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{entregaId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long entregaId) {
        entregaService.finalizar(entregaId);
    }

    //TODO implementar Swagger
    //TODO implementar Cancelamento
}
