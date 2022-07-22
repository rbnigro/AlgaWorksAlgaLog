package com.algaworks.algalog.domain.service;

import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.StatusEntrega;
import com.algaworks.algalog.domain.repository.EntregaReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SolicitacaoEntregaService {

    @Autowired
    private CatalogoClienteService catalogoClienteService;

    @Autowired
    private EntregaReository entregaReository;

    @Transactional
    public Entrega solicitarEntrega(Entrega entrega) {
        entrega.setCliente(catalogoClienteService.buscar(entrega.getCliente().getId()));
        entrega.setStatus(StatusEntrega.PENDENTE);
        entrega.setDataPedido(LocalDateTime.now());

        return entregaReository.save(entrega);
    }
}
