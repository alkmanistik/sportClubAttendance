package ru.avantys.sportClubAttendance.dto;

import ru.avantys.sportClubAttendance.model.Client;

import java.util.UUID;

public record ClientDto(
        UUID id,
        String fullName,
        String email,
        Boolean isBlocked
) {
    public static ClientDto from(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFullName(),
                client.getEmail(),
                client.getIsBlocked()
        );
    }

    public static Client toClient(ClientDto clientDto) {
        var client = new Client();
        client.setId(clientDto.id);
        client.setFullName(clientDto.fullName);
        client.setEmail(clientDto.email);
        client.setIsBlocked(clientDto.isBlocked);
        return client;
    }
}