package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.dto.OcorrenciaDTO;
import com.algaworks.algalog.domain.service.RegistroOcorrenciaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrega/{entregaId}/ocorrencias")
public class OcorrenciaController {

    @Autowired
    RegistroOcorrenciaService registroOcorrenciaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaDTO registrar (@PathVariable Long entregaId, @RequestBody OcorrenciaDTO ocorrenciaDTO) {
        var ocorrenciaRegistrada = registroOcorrenciaService.registrar(entregaId, ocorrenciaDTO.getDescricao());

        return modelMapper.map(ocorrenciaRegistrada, OcorrenciaDTO.class);
    }
}
