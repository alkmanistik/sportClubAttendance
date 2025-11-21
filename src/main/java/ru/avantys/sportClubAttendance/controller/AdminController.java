package ru.avantys.sportClubAttendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avantys.sportClubAttendance.dto.AccessRuleDto;
import ru.avantys.sportClubAttendance.dto.ClientDto;
import ru.avantys.sportClubAttendance.dto.MembershipDto;
import ru.avantys.sportClubAttendance.model.*;
import ru.avantys.sportClubAttendance.service.AccessService;
import ru.avantys.sportClubAttendance.service.ClientService;
import ru.avantys.sportClubAttendance.service.MembershipService;
import ru.avantys.sportClubAttendance.service.VisitService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ClientService clientService;
    private final MembershipService membershipService;
    private final AccessService accessService;
    private final VisitService visitService;

    public AdminController(ClientService clientService, MembershipService membershipService,
                           AccessService accessService, VisitService visitService) {
        this.clientService = clientService;
        this.membershipService = membershipService;
        this.accessService = accessService;
        this.visitService = visitService;
    }

    // Client endpoints
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody ClientDto clientDto) {
        try {
            Client client = clientService.createClient(clientDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clients/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clients/search")
    public ResponseEntity<List<Client>> searchClientsByName(@RequestParam String name) {
        List<Client> clients = clientService.searchClientsByName(name);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody ClientDto clientDto) {
        try {
            Client client = clientService.updateClient(id, clientDto);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/clients/{id}/block")
    public ResponseEntity<Client> blockClient(@PathVariable UUID id) {
        try {
            Client client = clientService.toggleBlockStatus(id, true);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/clients/{id}/unblock")
    public ResponseEntity<Client> unblockClient(@PathVariable UUID id) {
        try {
            Client client = clientService.toggleBlockStatus(id, false);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clients/count")
    public ResponseEntity<Long> getClientsCount() {
        long count = clientService.getClientsCount();
        return ResponseEntity.ok(count);
    }

    // Membership endpoints
    @PostMapping("/memberships")
    public ResponseEntity<Membership> createMembership(@RequestBody MembershipDto membershipDto) {
        try {
            Membership membership = membershipService.createMembership(membershipDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(membership);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/memberships")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        List<Membership> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/memberships/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable UUID id) {
        return membershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/memberships/client/{clientId}")
    public ResponseEntity<List<Membership>> getMembershipsByClient(@PathVariable UUID clientId) {
        List<Membership> memberships = membershipService.getMembershipsByClient(clientId);
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/memberships/type/{type}")
    public ResponseEntity<List<Membership>> getMembershipsByType(@PathVariable MembershipType type) {
        List<Membership> memberships = membershipService.getAllByType(type);
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/memberships/count")
    public ResponseEntity<Long> getMembershipsCount() {
        long count = membershipService.getMembershipsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/memberships/count/active")
    public ResponseEntity<Long> getActiveMembershipsCount() {
        long count = membershipService.getActiveMembershipsCount();
        return ResponseEntity.ok(count);
    }

    // AccessRule endpoints
    @PostMapping("/access-rules")
    public ResponseEntity<AccessRule> createAccessRule(@RequestBody AccessRuleDto accessRuleDto,
                                                       @RequestParam UUID membershipId) {
        try {
            AccessRule accessRule = accessService.createAccessRule(accessRuleDto, membershipId);
            return ResponseEntity.status(HttpStatus.CREATED).body(accessRule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/access-rules/membership/{membershipId}")
    public ResponseEntity<List<AccessRule>> getAccessRulesByMembership(@PathVariable UUID membershipId) {
        List<AccessRule> accessRules = accessService.getAccessRulesByMembership(membershipId);
        return ResponseEntity.ok(accessRules);
    }

    @DeleteMapping("/access-rules/{id}")
    public ResponseEntity<Void> deleteAccessRule(@PathVariable UUID id) {
        try {
            accessService.deleteAccessRule(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Visit endpoints
    @GetMapping("/visits/membership/{membershipId}")
    public ResponseEntity<List<Visit>> getVisitsByMembership(@PathVariable UUID membershipId) {
        List<Visit> visits = visitService.getVisitsByMembership(membershipId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/visits/membership/{membershipId}/count")
    public ResponseEntity<Long> getVisitCountByMembership(@PathVariable UUID membershipId) {
        long count = visitService.getVisitCountByMembership(membershipId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/visits/membership/{membershipId}/last")
    public ResponseEntity<Visit> getLastVisitByMembership(@PathVariable UUID membershipId) {
        return visitService.getLastVisitByMembership(membershipId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
