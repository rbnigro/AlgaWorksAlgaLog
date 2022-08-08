package com.algaworks.algalog.domain.service;

import com.algaworks.algalog.domain.exception.EntityNotFoundException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.StatusEntrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class EntregaService {

    @Autowired
    private ClienteService catalogoClienteService;

    @Autowired
    private EntregaRepository entregaReository;

    public Entrega buscar(Long entregaId) {
        return entregaReository.findById(entregaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada!"));
    }

    @org.springframework.transaction.annotation.Transactional
    public Entrega solicitarEntrega(Entrega entrega) {
        entrega.setCliente(catalogoClienteService.buscar(entrega.getCliente().getId()));
        entrega.setStatus(StatusEntrega.PENDENTE);
        entrega.setDataPedido(OffsetDateTime.now());

        return entregaReository.save(entrega);
    }

    @Transactional
    public void finalizar(Long entregaId) {
        var entrega = buscar(entregaId);
        entrega.finalizar();
        entregaReository.save(entrega);
    }

    public void cancelar(Long entregaId) {
        var entrega = buscar(entregaId);
        entrega.cancelar();
        entregaReository.save(entrega);
    }

    public List<Entrega> findAll() {
        return entregaReository.findAll();
    }
}
