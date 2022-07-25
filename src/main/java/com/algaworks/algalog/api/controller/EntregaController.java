package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaReository;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;
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
    private SolicitacaoEntregaService solicitacaoEntregaService;

    @PostMapping("/solicitar")
    @ResponseStatus(HttpStatus.CREATED)
    public Entrega solicitarEntrega(@Valid @RequestBody Entrega entrega) {
        return solicitacaoEntregaService.solicitarEntrega(entrega);
    }

    @GetMapping("/listar")
    public List<Entrega> listar() {
        return entregaReository.findAll();
    }

    @GetMapping("/listar/{entregaId}")
    public ResponseEntity<Entrega> buscar(@PathVariable Long entregaId) {
        return entregaReository.findById(entregaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
