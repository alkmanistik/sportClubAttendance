package ru.avantys.sportClubAttendance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avantys.sportClubAttendance.dto.MembershipDto;
import ru.avantys.sportClubAttendance.model.Client;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.MembershipType;
import ru.avantys.sportClubAttendance.repository.MembershipRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final ClientService clientService;

    public MembershipService(MembershipRepository membershipRepository, ClientService clientService) {
        this.membershipRepository = membershipRepository;
        this.clientService = clientService;
    }

    public Membership createMembership(MembershipDto membershipDto) {
        Client client = clientService.getClientById(membershipDto.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + membershipDto.clientId()));

        Membership membership = MembershipDto.toMembership(membershipDto, client);
        return membershipRepository.save(membership);
    }

    @Transactional(readOnly = true)
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Membership> getAllByType(MembershipType type) {
        return membershipRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Membership> getMembershipsByClient(UUID clientId) {
        return membershipRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public Optional<Membership> getMembershipById(UUID id) {
        return membershipRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public long getMembershipsCount() {
        return membershipRepository.count();
    }

    @Transactional(readOnly = true)
    public long getActiveMembershipsCount() {
        return membershipRepository.countActiveMemberships();
    }
}
