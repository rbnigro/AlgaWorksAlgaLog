package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.dto.OcorrenciaDTO;
import com.algaworks.algalog.domain.service.EntregaService;
import com.algaworks.algalog.domain.service.OcorrenciaService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/entrega/{entregaId}/ocorrencias")
@Api(value = "API REST Ocorrencias")
@CrossOrigin(origins = "*")
public class OcorrenciaController {

    @Autowired
    OcorrenciaService ocorrenciaService;

    @Autowired
    EntregaService entregaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaDTO registrar (@PathVariable Long entregaId, @RequestBody OcorrenciaDTO ocorrenciaDTO) {
        var ocorrenciaRegistrada = ocorrenciaService.registrar(entregaId, ocorrenciaDTO.getDescricao());

        return modelMapper.map(ocorrenciaRegistrada, OcorrenciaDTO.class);
    }

    @GetMapping("/listar")
    public List<OcorrenciaDTO> listar(@PathVariable Long entregaId) {
        var entrega = entregaService.buscar(entregaId);
        var lista = entrega.getOcorrencias();

        Type listType = new TypeToken<List<OcorrenciaDTO>>(){}.getType();
        return modelMapper.map(lista, listType);

        // https://stackoverflow.com/questions/47929674/modelmapper-mapping-list-of-entites-to-list-of-dto-objects
    }

}
