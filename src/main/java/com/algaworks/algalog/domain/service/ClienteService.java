package com.algaworks.algalog.domain.service;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
    }

    @Transactional
    public Cliente salvar(@NotNull Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

        if ((emailEmUso) && (cliente.getId() != null)) {
            throw new NegocioException("Já existe cliente com esse email");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> buscarNome(String clienteNome) {
        return clienteRepository.findByNome(clienteNome);
    }

    public boolean existsById(Long clienteId) {
        return clienteRepository.existsById(clienteId);
    }

    public List<Cliente> findByNomeContaining(String parteNomeCliente) {
        return clienteRepository.findByNomeContaining(parteNomeCliente);
    }

    public Optional<Cliente> findById(Long clienteId) {
        return clienteRepository.findById(clienteId);
    }
}
