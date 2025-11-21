package ru.avantys.sportClubAttendance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avantys.sportClubAttendance.dto.ClientDto;
import ru.avantys.sportClubAttendance.model.Client;
import ru.avantys.sportClubAttendance.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(ClientDto clientDto) {
        if (clientRepository.existsByEmail(clientDto.email())) {
            throw new IllegalArgumentException("Client with email " + clientDto.email() + " already exists");
        }
        Client client = ClientDto.toClient(clientDto);
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Client> searchClientsByName(String name) {
        return clientRepository.findByFullNameContainingIgnoreCase(name);
    }

    public Client updateClient(UUID id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        if (!client.getEmail().equals(clientDto.email()) && clientRepository.existsByEmail(clientDto.email())) {
            throw new IllegalArgumentException("Email " + clientDto.email() + " is already taken");
        }
        client.setFullName(clientDto.fullName());
        client.setEmail(clientDto.email());
        return clientRepository.save(client);
    }

    public Client toggleBlockStatus(UUID id, boolean isBlocked) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        client.setIsBlocked(isBlocked);
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public long getClientsCount() {
        return clientRepository.count();
    }

}