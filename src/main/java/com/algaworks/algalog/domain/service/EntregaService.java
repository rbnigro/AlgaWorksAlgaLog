package com.algaworks.algalog.domain.service;

import com.algaworks.algalog.domain.exception.EntityNotFoundException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    @Autowired
    private EntregaReository entregaReository;

    public Entrega buscar(Long entregaId) {
        return entregaReository.findById(entregaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada!"));
    }

}
