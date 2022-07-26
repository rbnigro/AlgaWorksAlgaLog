package com.algaworks.algalog.domain.service;

import com.algaworks.algalog.domain.model.Ocorrencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroOcorrenciaService {

    @Autowired
    EntregaService entregaService;

    @Transactional
    public Ocorrencia registrar(Long entregaId, String descricao) {
        var entrega = entregaService.buscar(entregaId);
        return entrega.adicionarOcorrencia(descricao);
    }

}
