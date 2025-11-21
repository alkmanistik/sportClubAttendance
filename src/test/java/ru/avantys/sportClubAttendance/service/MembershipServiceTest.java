package ru.avantys.sportClubAttendance.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.avantys.sportClubAttendance.dto.MembershipDto;
import ru.avantys.sportClubAttendance.model.Client;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.MembershipType;
import ru.avantys.sportClubAttendance.repository.MembershipRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    MembershipRepository membershipRepository;

    @Mock
    ClientService clientService;

    MembershipService membershipService;

    @BeforeEach
    public void setUp(){
        membershipService = new MembershipService(
                membershipRepository,
                clientService
        );
    }

    @Test
    public void valid_createMembership() {
        UUID clientId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID memberShipId = UUID.fromString("00000000-0000-0000-0000-100000000001");

        Client client = getRealClientWithoutMemberships(clientId).get();

        when(clientService.getClientById(clientId)).thenReturn(getRealClientWithoutMemberships(clientId));

        MembershipDto membershipDto = createMembership(memberShipId, clientId, client.getFullName());

        membershipService.createMembership(membershipDto);

        verify(membershipRepository, times(1)).save(any(Membership.class));
    }

    @Test
    public void invalid_createMembership_withNullClientId() {
        MembershipDto membershipDto = mock(MembershipDto.class);
        when(membershipDto.clientId()).thenReturn(null);

        when(clientService.getClientById(null)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            membershipService.createMembership(membershipDto);
        });

        verify(membershipRepository, never()).save(any(Membership.class));
    }

    //TODO Написать логику проверки пользователя (Заблокирован/Не заблокирован) (setIsBlocked) и
    // написать тесты для проверки бизнес логики

    Optional<Client> getRealClientWithoutMemberships(UUID clientId){
        Client client = new Client();
        client.setId(clientId);
        client.setFullName("TEST");
        client.setEmail("test@email");
        client.setIsBlocked(false);
        client.setMemberships(Collections.emptyList());
        return Optional.of(client);
    }

    MembershipDto createMembership(UUID memberShipId, UUID clientId, String clientName){
        return new MembershipDto(
                memberShipId,
                clientId,
                clientName,
                MembershipType.STANDARD,
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(1),
                10
        );
    }
}