package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.dto.EntregaDTO;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.service.EntregaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/entregas")
@Api(value = "API REST Entregas")
@CrossOrigin(origins = "*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/solicitar")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Solicita Entrega")
    public EntregaDTO solicitarEntrega(@Valid @RequestBody Entrega entrega) {
        var entregaRetorno = entregaService.solicitarEntrega(entrega);
        return modelMapper.map(entregaRetorno, EntregaDTO.class);
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Retorna Lista Entregas")
    public List<EntregaDTO> listar() {
        var entregas = entregaService.findAll();
        Type listType = new TypeToken<List<EntregaDTO>>(){}.getType();
        return modelMapper.map(entregas, listType);
    }

    @GetMapping("/listar/{entregaId}")
    @ApiOperation(value = "Retorna Entrega Específica")
    public ResponseEntity<EntregaDTO> buscar(@PathVariable Long entregaId) {
        return entregaService.findById(entregaId)
                .map(entrega -> {
                    var entregaDTO = modelMapper.map(entrega, EntregaDTO.class);

                    return ResponseEntity.ok(entregaDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{entregaId}/finalizar")
    @ApiOperation(value = "Finaliza Entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long entregaId) {
        entregaService.finalizar(entregaId);
    }

    @PutMapping("/{entregaId}/cancelar")
    @ApiOperation(value = "Cancela Entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long entregaId) {
        entregaService.cancelar(entregaId);
    }

    //TODO retirar repository
 }
